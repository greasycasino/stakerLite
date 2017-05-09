package Core;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import static Core.Program.*;


public class Controller implements Initializable {

    @FXML //fx:id="p1Name"
    public TextField p1Name;
    @FXML //fx:id="p2Name"
    public TextField p2Name;
    @FXML //fx:id="p1Attack"
    public TextField p1Attack;
    @FXML //fx:id="p2Attack"
    public TextField p2Attack;
    @FXML //fx:id="p1Strength"
    public TextField p1Strength;
    @FXML //fx:id="p2Strength"
    public TextField p2Strength;
    @FXML //fx:id="p1Defence"
    public TextField p1Defence;
    @FXML //fx:id="p2Defence"
    public TextField p2Defence;
    @FXML //fx:id="p1HP"
    public TextField p1HP;
    @FXML //fx:id="p2HP"
    public TextField p2HP;
    @FXML //fx:id="p1Wager"
    public TextField p1Wager;
    @FXML //fx:id="p2wWger"
    public TextField p2Wager;

    @FXML //fx:id="p1Style"
    public MenuButton p1Style;
    @FXML //fx:id="p1Accurate"
    public MenuItem p1Accurate;
    @FXML //fx:id="p1Aggressive"
    public MenuItem p1Aggressive;
    @FXML //fx:id="p1Controlled"
    public MenuItem p1Controlled;
    @FXML //fx:id="p1Defensive"
    public MenuItem p1Defensive;
    @FXML //fx:id="p1RotateAD"
    public MenuItem p1RotateAD;
    @FXML //fx:id="p1RotateCD"
    public MenuItem p1RotateCD;

    @FXML //fx:id="p2Style"
    public MenuButton p2Style;
    @FXML //fx:id="p2Accurate"
    public MenuItem p2Accurate;
    @FXML //fx:id="p2Aggressive"
    public MenuItem p2Aggressive;
    @FXML //fx:id="p2Controlled"
    public MenuItem p2Controlled;
    @FXML //fx:id="p2Defensive"
    public MenuItem p2Defensive;
    @FXML //fx:id="p2RotateAD"
    public MenuItem p2RotateAD;
    @FXML //fx:id="p2RotateCD"
    public MenuItem p2RotateCD;

    @FXML //fx:id="duelType"
    public MenuButton duelType;
    @FXML //fx:id="tentSpecOff"
    public MenuItem tentSpecOff;
    @FXML //fx:id="tentSpecOn"
    public MenuItem tentSpecOn;
    @FXML //fx:id="dScimSpecOff"
    public MenuItem dScimSpecOff;
    @FXML //fx:id="dScimSpecOn"
    public MenuItem dScimSpecOn;
    @FXML //fx:id="noWeapon"
    public MenuItem noWeapon;

    @FXML //fx:id="searchButton"
    public Button searchButton;
    @FXML //fx:id="runButton"
    public Button runButton;
    @FXML //fx:id="clearButton"
    public Button clearButton;
    @FXML //fx:id="donateButton"
    public Button donateButton;

    @FXML //fx:id="textArea"
    public TextArea textArea;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tentSpecOn.setDisable(true); //TODO Create spec methods
        dScimSpecOn.setDisable(true); //Create spec methods
        p1Wager.setTooltip(new Tooltip("Enter Wager (Mil)"));
        p2Wager.setTooltip(new Tooltip("Enter Wager (Mil)"));

        //Player 1 Selections//
        p1Accurate.setOnAction(event -> updateMenu(p1Style, p1Accurate));
        p1Aggressive.setOnAction(event -> updateMenu(p1Style, p1Aggressive));
        p1Controlled.setOnAction(event -> updateMenu(p1Style, p1Controlled));
        p1Defensive.setOnAction(event -> updateMenu(p1Style, p1Defensive));
        p1RotateAD.setOnAction(event -> updateMenu(p1Style, p1RotateAD));
        p1RotateCD.setOnAction(event -> updateMenu(p1Style, p1RotateCD));

        //Player 2 Selections//
        p2Accurate.setOnAction(event -> updateMenu(p2Style, p2Accurate));
        p2Aggressive.setOnAction(event -> updateMenu(p2Style, p2Aggressive));
        p2Controlled.setOnAction(event -> updateMenu(p2Style, p2Controlled));
        p2Defensive.setOnAction(event -> updateMenu(p2Style, p2Defensive));
        p2RotateAD.setOnAction(event -> updateMenu(p2Style, p2RotateAD));
        p2RotateCD.setOnAction(event -> updateMenu(p2Style, p2RotateCD));

        //Fight Type Selections
        tentSpecOff.setOnAction(event -> updateMenu(duelType, tentSpecOff));
        tentSpecOn.setOnAction(event -> updateMenu(duelType, tentSpecOn));
        dScimSpecOff.setOnAction(event -> updateMenu(duelType, dScimSpecOff));
        dScimSpecOn.setOnAction(event -> updateMenu(duelType, dScimSpecOn));
        noWeapon.setOnAction(event -> updateMenu(duelType, noWeapon));

        runButton.setOnAction(event -> {
            try {
                run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        searchButton.setOnAction(event -> updateSearch());

        searchButton.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
               updateSearch();
            }
        });

        runButton.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                try {
                    run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        clearButton.setOnAction(event -> reset());
        donateButton.setOnAction(event -> donation());

    }

    public void run() throws IOException {

        Player p1 = new Player();
        Player p2 = new Player();
        Map p1Map = newItemMap(p1Style);
        Map p2Map = newItemMap(p2Style);
        Map dtMap = newItemMap(duelType);

        if(checkStyles() && checkValues() && checkType(duelType) && checkStats()) {

            retrieveStats(p1, p1Attack, p1Strength, p1Defence, p1HP);
            retrieveStats(p2, p2Attack, p2Strength, p2Defence, p2HP);
            p1Style.getItems().stream().filter(item -> Objects.equals(p1Style.getId(), item.getId())).forEach(item -> retrieveStyle(p1Map, item, p1));
            p2Style.getItems().stream().filter(item -> Objects.equals(p2Style.getId(), item.getId())).forEach(item -> retrieveStyle(p2Map, item, p2));
            duelType.getItems().stream().filter(item -> Objects.equals(duelType.getId(), item.getId())).forEach(item -> retrieveDuelType(dtMap, item, p1, p2));

            calcMaxHit(p1);
            calcMaxHit(p2);
            calcRolls(p1);
            calcRolls(p2);
            calcAccuracy(p1, p2);
            for (int i = 0; i < 2000000; i++)
                if (i % 2 == 0) duel(p1, p2);
                else if (i % 2 == 1) duel(p2, p1);


            p1.setName(p1Name.getText());
            p2.setName(p2Name.getText());
            p1.setPercentOdds((double) p1.getWin() / p1.totalDuels);
            p2.setPercentOdds((double) p2.getWin() / p2.totalDuels);
            updateText(results(p1,p2,Integer.parseInt(p1Wager.getText()), Integer.parseInt(p2Wager.getText())));

        }
    }

    private boolean checkStyles() {
        if(!Objects.equals(duelType.getId(), tentSpecOff.getId()) || !Objects.equals(p1Style.getId(), p1Aggressive.getId()) && !Objects.equals(p2Style.getId(), p2Aggressive.getId()) && !p1Style.getText().equals("Style") && !p2Style.getText().equals("Style")){
            return true;
        }
        updateText("(ERROR) Select Appropriate Attack Styles\n" + "(NOTE): Whip CANNOT be set to AGGRESSIVE");
        return false;
    }

    private boolean checkType(MenuButton m) {
        if (!m.getText().equals("Fight Type")){
            return true;
        }

        updateText("Select 'Fight Type'");
        return false;
    }


    private boolean checkValues() {
        if (p1Name.getText().length() == 0){ p1Name.setText("Player 1"); }
        if (p2Name.getText().length() == 0){ p2Name.setText("Player 2"); }

        if(tfLength(p1Attack) != 0 && tfLength(p1Strength) != 0 && tfLength(p1Defence) != 0 && tfLength(p1HP) != 0 && tfLength(p2Attack) != 0 && tfLength(p2Strength) != 0 && tfLength(p2Defence) != 0 && tfLength(p2HP) != 0) {
            return true;
        }

        updateText("All level must be assigned numeric values");
        return false;
    }

    private boolean checkStats(){
        TextField[] playerStats = new TextField[8];
        playerStats[0] = p1Attack;
        playerStats[1] = p1Strength;
        playerStats[2] = p1Defence;
        playerStats[3] = p1HP;
        playerStats[4] = p2Attack;
        playerStats[5] = p2Strength;
        playerStats[6] = p2Defence;
        playerStats[7] = p2HP;

        for (TextField t : playerStats) {
            if (Double.parseDouble(t.getText()) < 0 || Double.parseDouble(t.getText()) > 99) {
                updateText("invalid entry: " + t.getId() + " (" + t.getText() + ")");
                return false;
            }
        }

        return true;
    }

    private void updateMenu(MenuButton b, MenuItem m) {
        b.setId(m.getId());
        b.setText(m.getText());

    }

    private void updateText(String s) {
        textArea.clear();
        textArea.setText(s);
    }

    private void updatePlayer(int[] stats, TextField att, TextField str, TextField def, TextField hp) {
        att.setText(String.valueOf(stats[0]));
        def.setText(String.valueOf(stats[1]));
        str.setText(String.valueOf(stats[2]));
        hp.setText(String.valueOf(stats[3]));
    }

    private void updateSearch(){
        try {
            if (p1Name.getCharacters().length() != 0)
                updatePlayer(searchHiscores(p1Name.getText()), p1Attack, p1Strength, p1Defence, p1HP);
            if (p2Name.getCharacters().length() != 0)
                updatePlayer(searchHiscores(p2Name.getText()), p2Attack, p2Strength, p2Defence, p2HP);
        } catch (IOException e) {
            textArea.clear();
            updateText("Error Retrieving Hiscores...");
        }
    }

    private static int tfLength(TextField t) {
        return t.getText().length();
    }

    private void reset() {
        p1Name.clear();
        p2Name.clear();
        p1Name.clear();
        p2Name.clear();
        p1Attack.clear();
        p2Attack.clear();
        p1Strength.clear();
        p2Strength.clear();
        p1Defence.clear();
        p2Defence.clear();
        p1HP.clear();
        p2HP.clear();
        p1Style.setText("Style");
        p2Style.setText("Style");
        duelType.setText("Duel Type");
        textArea.clear();
    }


}
