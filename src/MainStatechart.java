import java.util.*;

/**
 * Created by Ezra Steinmetz and Tomer Trabelsky on 22-May-17.
 */
public class MainStatechart extends Thread {

    private static MainStatechart lastInstance;
    final long DELAY = 10000;
    final int starvationLimit = 2;
    List<WalkersLight> independentLights;
    Event64 eventReciver = new Event64();
    int fSkipCounter = 0, KNSkipCounter = 0;
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

    public static void buttonPressAlert(int buttonKey) {
        MainSCEvent event = null;
        //TODO take care of all cases and implement the events in the statechart too
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
        MainSCEvent event = null;
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

    public void sendEvent(MainSCEvent event) {
        eventReciver.sendEvent(event);
    }

    private MainSCEvent runRegularMode(List<MainSCEvent> exitEvents) {
        Timer timer = new Timer();
        MainSCEvent event = null;
        YeminAvotLight.sendEvent(CarsEvent.TurnRed);
        FarbsteinLight.sendEvent(CarsEvent.TurnRed);
        KNSideLight.sendEvent(CarsEvent.TurnGreen);
        independentLights.forEach(
                (WL) -> WL.sendEvent(WalkersLightEvent.TurnGreen)
        );
        KanfeiNesharimLight.sendEvent(CarsEvent.TurnGreen);


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
                    if (event == MainSCEvent.KNTimeout || event == MainSCEvent.KNPress || event == MainSCEvent.GoToYA) {
                        if (event == MainSCEvent.KNTimeout) {
                            KNSkipCounter = 0;
                        }
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                sendEvent(MainSCEvent.YATimeout);
                            }
                        }, DELAY);
                        YeminAvotLight.sendEvent(CarsEvent.TurnGreen);
                        KanfeiNesharimLight.sendEvent(CarsEvent.TurnRed);
                        KNSideLight.sendEvent(CarsEvent.TurnRed);
                        for (WalkersLight WL : independentLights) {
                            WL.sendEvent(WalkersLightEvent.TurnRed);
                        }
                        state = RegularSubState.YeminAvot;
                    }
                    break;
                case YeminAvot:
                    event = (MainSCEvent) eventReciver.waitEvent();
                    if (event == MainSCEvent.YATimeout || event == MainSCEvent.YAPress) {
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                sendEvent(MainSCEvent.FTimeout);
                            }
                        }, DELAY);
                        YeminAvotLight.sendEvent(CarsEvent.TurnRed);
                        KNSideLight.sendEvent(CarsEvent.TurnGreen);
                        FarbsteinLight.sendEvent(CarsEvent.TurnGreen);
                        state = RegularSubState.Farbstein;
                    }
                    if (event == MainSCEvent.GoToKN && fSkipCounter < starvationLimit) {
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                sendEvent(MainSCEvent.KNTimeout);
                            }
                        }, DELAY);
                        YeminAvotLight.sendEvent(CarsEvent.TurnRed);
                        KanfeiNesharimLight.sendEvent(CarsEvent.TurnGreen);
                        KNSideLight.sendEvent(CarsEvent.TurnGreen);
                        for (WalkersLight WL : independentLights) {
                            WL.sendEvent(WalkersLightEvent.TurnGreen);
                        }
                        state = RegularSubState.YeminAvot;
                    }
                    break;
                case Farbstein:
                    event = (MainSCEvent) eventReciver.waitEvent();
                    if (event == MainSCEvent.FTimeout || event == MainSCEvent.GoToKN) {
                        if (event == MainSCEvent.FTimeout) {
                            fSkipCounter = 0;
                        }
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                sendEvent(MainSCEvent.KNTimeout);
                            }
                        }, DELAY);
                        FarbsteinLight.sendEvent(CarsEvent.TurnRed);
                        KanfeiNesharimLight.sendEvent(CarsEvent.TurnGreen);
                        for (WalkersLight WL : independentLights) {
                            WL.sendEvent(WalkersLightEvent.TurnGreen);
                        }
                        state = RegularSubState.KanfeiNesharim;
                    }
                    if (event == MainSCEvent.GoToYA && KNSkipCounter < starvationLimit) {
                        KNSkipCounter++;
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                sendEvent(MainSCEvent.YATimeout);
                            }
                        }, DELAY);
                        FarbsteinLight.sendEvent(CarsEvent.TurnGreen);
                        KanfeiNesharimLight.sendEvent(CarsEvent.TurnRed);
                        for (WalkersLight WL : independentLights) {
                            WL.sendEvent(WalkersLightEvent.TurnRed);
                        }
                        state = RegularSubState.KanfeiNesharim;
                    }

                    break;
            }
        } while (!exitEvents.contains(event));
        return event;
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
