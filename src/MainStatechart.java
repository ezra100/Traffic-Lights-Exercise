import java.util.*;

import com.google.common.base.Stopwatch;
/**
 * Created by Ezra Steinmetz and Tomer Trabelsy on 22-May-17.
 */
public class MainStatechart extends Thread {

    private static MainStatechart lastInstance;
    final int starvationLimit = 2;
    final int starvationThreshold = 5;
    private final long DELAY = 10000;
    List<WalkersLight> independentLights;
    Event64 eventReciver = new Event64();
    int FSkipCounter = 0, KNSkipCounter = 0, YASkipCounter = 0;
    Stopwatch YASWatch = Stopwatch.createUnstarted(),
            KNSWatch = Stopwatch.createUnstarted(), FSWatch = Stopwatch.createUnstarted();
    Timer timer = new Timer();
    private CarsLight YeminAvotLight, KanfeiNesharimLight, KNSideLight, FarbsteinLight;


    public MainStatechart(CarsLight yeminAvotLight, CarsLight farbsteinLight, CarsLight KNSideLight, CarsLight kanfeiNesharimLight, List<WalkersLight> independentLights) {
        super("Main statechart");
        YeminAvotLight = yeminAvotLight;
        KanfeiNesharimLight = kanfeiNesharimLight;
        FarbsteinLight = farbsteinLight;
        this.independentLights = independentLights;
        this.KNSideLight = KNSideLight;
        lastInstance = this;
    }

    static void buttonPressAlert(int buttonKey) {
        MainSCEvent event = null;
        switch (buttonKey) {
            case 4:
            case 5:
                event = MainSCEvent.YAPress;
                break;
            case 12:
            case 13:
            case 6:
            case 7:
                event = MainSCEvent.KNPress;
                break;
            case 8:
            case 11:
            case 14:
            case 15:
                event = MainSCEvent.GoToKN;
                break;
            case 9:
            case 10:
                event = MainSCEvent.GoToYA;
        }
        lastInstance.sendEvent(event);
    }

    static void shbatPress() {
        lastInstance.sendEvent(MainSCEvent.ShabatButtonPress);
    }

    public void run() {
        System.out.println("Main statechart started");
        YeminAvotLight.start();
        KanfeiNesharimLight.start();
        FarbsteinLight.start();
        KNSideLight.start();
        for (WalkersLight WL : independentLights) {
            WL.start();
        }
        ExternalState state = ExternalState.RegularMode;
        MainSCEvent event;
        //noinspection InfiniteLoopStatement
        while (true) {
            switch (state) {
                case RegularMode:
                    event = runRegularMode(Collections.singletonList(MainSCEvent.ShabatButtonPress));
                    if (event == MainSCEvent.ShabatButtonPress) {
                        YeminAvotLight.sendEvent(CarsEvent.ShabatMode);
                        KanfeiNesharimLight.sendEvent(CarsEvent.ShabatMode);
                        FarbsteinLight.sendEvent(CarsEvent.ShabatMode);
                        KNSideLight.sendEvent(CarsEvent.ShabatMode);
                        for (WalkersLight WL : independentLights) {
                            WL.sendEvent(WalkersLightEvent.ShabatMode);
                        }
                        state = ExternalState.ShabatMode;
                    }

                    break;
                case ShabatMode:
                    event = (MainSCEvent) eventReciver.waitEvent();
                    if (event == MainSCEvent.ShabatButtonPress) {
                        YeminAvotLight.sendEvent(CarsEvent.RegularMode);
                        KanfeiNesharimLight.sendEvent(CarsEvent.RegularMode);
                        FarbsteinLight.sendEvent(CarsEvent.RegularMode);
                        KNSideLight.sendEvent(CarsEvent.RegularMode);
                        for (WalkersLight WL : independentLights) {
                            WL.sendEvent(WalkersLightEvent.RegularMode);
                        }
                        state = ExternalState.RegularMode;
                    }
                    break;
            }
        }
    }

    private void sendEvent(MainSCEvent event) {
        eventReciver.sendEvent(event);
    }

    private MainSCEvent runRegularMode(List<MainSCEvent> exitEvents) {
        MainSCEvent event = null;
        goToKN();
        RegularSubState state = RegularSubState.KanfeiNesharim;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sendEvent(MainSCEvent.KNTimeout);
            }
        }, DELAY);
        do {
            switch (state) {
                case KanfeiNesharim:
                    event = (MainSCEvent) eventReciver.waitEvent();
                    if ((event == MainSCEvent.KNTimeout || event == MainSCEvent.KNPress || event == MainSCEvent.GoToYA)
                            && (KNSkipCounter < starvationLimit || KNSWatch.elapsed().getSeconds() > starvationThreshold)) {
                        if (KNSWatch.elapsed().getSeconds() > starvationThreshold) {
                            KNSkipCounter = 0;
                        } else {
                            KNSkipCounter++;
                        }
                        goToYA();
                        state = RegularSubState.YeminAvot;
                    }
                    break;
                case YeminAvot:
                    event = (MainSCEvent) eventReciver.waitEvent();
                    if ((event == MainSCEvent.YATimeout || event == MainSCEvent.YAPress)
                            && (YASkipCounter < starvationLimit || YASWatch.elapsed().getSeconds() > starvationThreshold)) {
                        if (YASWatch.elapsed().getSeconds() > starvationThreshold) {
                            YASkipCounter = 0;
                        } else {
                            YASkipCounter++;
                        }
                        goToFarbstein();
                        state = RegularSubState.Farbstein;
                    }
                    if (event == MainSCEvent.GoToKN && FSkipCounter < starvationLimit
                            && (YASkipCounter < starvationLimit || YASWatch.elapsed().getSeconds() > starvationThreshold)) {
                        goToKN();
                        state = RegularSubState.KanfeiNesharim;
                    }
                    break;
                case Farbstein:
                    event = (MainSCEvent) eventReciver.waitEvent();
                    if ((event == MainSCEvent.FTimeout || event == MainSCEvent.GoToKN)
                            && (FSkipCounter < starvationLimit || FSWatch.elapsed().getSeconds() > starvationThreshold)) {
                        if (FSWatch.elapsed().getSeconds() > starvationThreshold) {
                            FSkipCounter = 0;
                        } else {
                            FSkipCounter++;
                        }
                        goToKN();
                        state = RegularSubState.KanfeiNesharim;
                    }
                    if (event == MainSCEvent.GoToYA && KNSkipCounter < starvationLimit &&
                            (FSkipCounter < starvationLimit || FSWatch.elapsed().getSeconds() > starvationThreshold
                            )) {
                        if (FSWatch.elapsed().getSeconds() > starvationThreshold) {
                            FSkipCounter = 0;
                        } else {
                            FSkipCounter++;
                        }
                        KNSkipCounter++;
                        goToYA();
                        state = RegularSubState.YeminAvot;
                    }
                    break;
            }
        } while (!exitEvents.contains(event));
        return event;
    }

    private void goToYA() {
        KNSWatch.reset();
        FSWatch.reset();
        YASWatch.reset();
        YASWatch.start();
        independentLights.forEach(WL -> WL.sendEvent(WalkersLightEvent.TurnRed));
        KNSideLight.sendEvent(CarsEvent.TurnRed);
        KanfeiNesharimLight.sendEvent(CarsEvent.TurnRed);
        FarbsteinLight.sendEvent(CarsEvent.TurnRed);
        YeminAvotLight.sendEvent(CarsEvent.TurnGreen);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sendEvent(MainSCEvent.YATimeout);
            }
        }, DELAY);
    }

    private void goToKN() {
        KNSWatch.reset();
        FSWatch.reset();
        YASWatch.reset();
        KNSWatch.start();
        independentLights.forEach(WL -> WL.sendEvent(WalkersLightEvent.TurnGreen));
        KNSideLight.sendEvent(CarsEvent.TurnGreen);
        KanfeiNesharimLight.sendEvent(CarsEvent.TurnGreen);
        FarbsteinLight.sendEvent(CarsEvent.TurnRed);
        YeminAvotLight.sendEvent(CarsEvent.TurnRed);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sendEvent(MainSCEvent.KNTimeout);
            }
        }, DELAY);
    }

    private void goToFarbstein() {
        KNSWatch.reset();
        FSWatch.reset();
        YASWatch.reset();
        FSWatch.start();
        independentLights.forEach(WL -> WL.sendEvent(WalkersLightEvent.TurnRed));
        KNSideLight.sendEvent(CarsEvent.TurnGreen);
        KanfeiNesharimLight.sendEvent(CarsEvent.TurnRed);
        FarbsteinLight.sendEvent(CarsEvent.TurnGreen);
        YeminAvotLight.sendEvent(CarsEvent.TurnRed);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sendEvent(MainSCEvent.FTimeout);
            }
        }, DELAY);
    }

    /**
     * states of the regular sub statechart, the states are called after the street which has a cars' green light
     * when the state is active
     */
    private enum RegularSubState {
        KanfeiNesharim, YeminAvot, Farbstein
    }

    private enum ExternalState {RegularMode, ShabatMode}
}
