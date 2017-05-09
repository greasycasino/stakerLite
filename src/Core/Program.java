package Core;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Program {

    public static int[] searchHiscores(String name) throws IOException {
        String emptyURL = "http://services.runescape.com/m=hiscore_oldschool/index_lite.ws?player=";
        URL playerURL = new URL(emptyURL.concat(name.replaceAll(" ", "+")));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(playerURL.openStream()));

        String inputLine;
        int[] stats = new int[4];
        for (int i = 0; i < 5; i++) {
            inputLine = in.readLine();
            if (i >= 1 && i < 5) {
                String[] values = inputLine.split(",");
                stats[i - 1] = Integer.parseInt(values[1]);
            }
        }

        return stats;
    }

    public static void calcRolls(Player p) {
        int effAtk = p.getAtt() + p.getAttackStyle().getAttackBonus() + 8;
        int effDef = p.getDef() + p.getAttackStyle().getDefenceBonus() + 8;
        int styleBonus = 0;

        if (p.getWeapon() == Weapon.Tentacle) {
            styleBonus = Weapon.Tentacle.getSlashBonus();
        } else if (p.getWeapon() == Weapon.DScim) {
            styleBonus = Weapon.DScim.getSlashBonus();
        } else if (p.getWeapon() == Weapon.DDS) {
            styleBonus = Weapon.DDS.getStabBonus();
        }

        if (p.getWeapon() != Weapon.DScim) {
            p.setMaxDefRoll((effDef) * (64));
        } else {
            p.setMaxDefRoll((effDef) * (64 + 1));
        }

        p.setMaxAttRoll((effAtk) * (styleBonus + 64));

    }

    public static void calcAccuracy(Player p1, Player p2) {
        if (p1.getMaxAttRoll() < p2.getMaxDefRoll()) {
            p1.setAccuracy(0.5 * p1.getMaxAttRoll() / p2.getMaxDefRoll());
        } else if (p1.getMaxAttRoll() > p2.getMaxDefRoll()) {
            p1.setAccuracy(1 - 0.5 * ((double) p2.getMaxDefRoll() / (double) p1.getMaxAttRoll()));
        }

        if (p2.getMaxAttRoll() < p1.getMaxDefRoll()) {
            p2.setAccuracy(0.5 * p2.getMaxAttRoll() / p1.getMaxDefRoll());
        } else if (p2.getMaxAttRoll() > p1.getMaxDefRoll()) {
            p2.setAccuracy(1 - 0.5 * ((double) p1.getMaxDefRoll() / (double) p2.getMaxAttRoll()));
        }

    }

    public static void duel(Player p1, Player p2) {
        Random r = new Random();

        while (p1.getHp() > 0 && p2.getHp() > 0) {  //While both are still alive

            if (p1.getHp() > 0) { //if p1 has hp > 0 (ie; still alive)
                if (p1.getAccuracy() > r.nextDouble()) { //roll random double and see if p1 is accurate
                    p2.setHp(p2.getHp() - (r.nextInt(p1.getMaxHit() + 1))); //set p2's hp to his current hp, minus a random hit from 0 to p1's max.
                }
            }

            if (p2.getHp() > 0) { //if p2 has hp > 0 (ie; still alive)
                if ((p2.getAccuracy()) > r.nextDouble()) { //roll random double and see if p2 is accurate
                    p1.setHp(p1.getHp() - (r.nextInt(p2.getMaxHit() + 1))); //set p1's hp to his current hp, minus a random hit from 0 to p2's max.
                }
            }

            if (p1.getHp() > 0 && p2.getHp() <= 0) { //if p1 is alive and p2 is dead give p1 a win, and a pid win
                p1.setWin(p1.getWin() + 1);
                if (p1.getPid()) {
                    p1.setPidWin(p1.getPidWin() + 1);
                }

            } else if (p1.getHp() <= 0 && p2.getHp() > 0) { //if p1 is dead and p2 is alive give p2 a win, and a pid win
                p2.setWin(p2.getWin() + 1);
                if (p2.getPid()) {
                    p2.setPidWin(p2.getPidWin() + 1);
                }
            }

        }

        p1.setTotalDuels(p1.getTotalDuels() + 1);
        p2.setTotalDuels(p2.getTotalDuels() + 1);

        p1.reset();
        p2.reset();

    }

    public static void calcMaxHit(Player p) {
        int weaponStrBonus = p.getWeapon().getStrBonus();
        double multiplier = p.getWeapon().getMultiplier();

        int effStr = p.getStr() + 8 + p.getAttackStyle().getStrengthBonus();
        p.setMaxHit((int) ((int) (Math.floor(5 + effStr + effStr * weaponStrBonus / 64.0) / 10.0) * multiplier));
    }

    public static void retrieveStyle(Map m, MenuItem i, Player p) {
        int key = (int) m.get(i);

        if (key == 1) {
            p.setAttackStyle(AttackStyle.Accurate);
        } else if (key == 2) {
            p.setAttackStyle(AttackStyle.Aggressive);
        } else if (key == 3) {
            p.setAttackStyle(AttackStyle.Controlled);
        } else if (key == 4) {
            p.setAttackStyle(AttackStyle.Defensive);
        } else if (key == 5) {
            p.setAttackStyle(AttackStyle.RotatingA);
        } else if (key == 6) {
            p.setAttackStyle(AttackStyle.RotatingC);
        }

    }

    public static void retrieveDuelType(Map m, MenuItem i, Player p1, Player p2) {
        int key = (int) m.get(i);

        if (key == 1) {
            p1.setWeapon(Weapon.Tentacle);
            p2.setWeapon(Weapon.Tentacle);
        } else if (key == 3) {
            p1.setWeapon(Weapon.DScim);
            p2.setWeapon(Weapon.DScim);
        } else if (key == 2 || key == 4) {
            p1.setWeapon(Weapon.DDS);
            p2.setWeapon(Weapon.DDS);
        } else if (key == 5) {
            p1.setWeapon(Weapon.NONE);
            p2.setWeapon(Weapon.NONE);
        }
    }

    public static void retrieveStats(Player p, TextField att, TextField str, TextField def, TextField hp) {
        p.setAtt(Integer.parseInt(String.valueOf(att.getCharacters())));
        p.setStr(Integer.parseInt(String.valueOf(str.getCharacters())));
        p.setDef(Integer.parseInt(String.valueOf(def.getCharacters())));
        p.setHp(Integer.parseInt(String.valueOf(hp.getCharacters())));
        p.setMaxHP(Integer.parseInt(String.valueOf(hp.getCharacters())));

    }

    /* Takes the MenuButton parameter accepted by the method and adds each MenuItem value to a hashmap, and stores
    * each value with an incrementing key. Useful for passing values around. */
    public static Map<MenuItem, Integer> newItemMap(MenuButton m) {
        Map<MenuItem, Integer> selections = new HashMap<>();
        int k = 0;
        for (MenuItem i : m.getItems()) {
            selections.put(i, k + 1);
            k++;
        }
        return selections; //MenuButton's list of MenuItems (descending) with ascending key values. ie; top item = 1
    }

    public static String toCaps(String s) {

        StringBuilder res = new StringBuilder();
        String[] strArr = s.split(" ");
        for (String str : strArr) {
            char[] stringArray = str.trim().toCharArray();
            stringArray[0] = Character.toUpperCase(stringArray[0]);
            str = new String(stringArray);

            res.append(str).append(" ");
        }

        return res.toString().trim();
    }

    public static void donation() {
        String url = "https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=MKH64GDXZGF6A&lc=US&item_name=stakerLite&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted";

        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static double roundValue(Double s) {
        DecimalFormat f = new DecimalFormat("##.00");
        return Double.valueOf(f.format(s * 100));
    }

    public static String results(Player p1, Player p2, int p1Wager, int p2Wager){
        double p1EV = (p1.getPercentOdds() * p2Wager*1000000) - (p2.getPercentOdds() * p1Wager*1000000);
        double p2EV = (p2.getPercentOdds() * p1Wager*1000000) - (p1.getPercentOdds() * p2Wager*1000000);

        return toCaps(p1.getName()) + ": " + roundValue(p1.getPercentOdds()) + "% " + "(EV: " + ((int)p1EV / 1000) + "k)" +
                "\n" + toCaps(p2.getName()) + ": " + roundValue(p2.getPercentOdds()) + "% " + "(EV: " + ((int)p2EV / 1000) + "k)" +
                "\n" + toCaps(p1.getName()) + " 3-Streak: " + roundValue(Math.pow(p1.getPercentOdds(),3)) + "%" +
                "\n" + toCaps(p2.getName()) + " 3-Streak: " + roundValue(Math.pow(p2.getPercentOdds(),3)) + "%";

    }



}


