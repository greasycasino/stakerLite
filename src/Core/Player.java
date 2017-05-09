package Core;

import java.text.NumberFormat;

public class Player {

    public String name;
    public int att, str, def, maxHP, hp;
    Weapon weapon;
    AttackStyle attackStyle;
    public int maxHit, maxAttRoll, maxDefRoll;
    public double accuracy;
    public int spec = 100;
    public int attacks = 0; //use for dds specs, could create an attack tick value for a more advanced version
    public boolean pid = false;
    public int win = 0;
    public int loss = 0;
    public int totalDuels = 0;
    public double percentOdds = 0.00;
    public int pidWin = 0;

    public Player() {

    }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public void setAtt(int att) { this.att = att; }
    public int getAtt() { return att; }

    public void setStr(int str) { this.str = str; }
    public int getStr() {return str; }

    public void setDef(int def) { this.def = def; }
    public int getDef() { return def; }

    public void setHp(int hp) { this.hp = hp; }
    public int getHp() { return hp; }

    public void setMaxHP(int maxHP) { this.maxHP = maxHP; }
    public int getMaxHP() { return maxHP; }

    public void setAttackStyle(AttackStyle attackStyle) { this.attackStyle = attackStyle; }
    public AttackStyle getAttackStyle() { return attackStyle; }

    public void setWeapon(Weapon weapon) { this.weapon = weapon; }
    public Weapon getWeapon() { return weapon; }

    public void setMaxHit(int maxHit) { this.maxHit = maxHit; }
    public int getMaxHit() { return maxHit; }

    public void setMaxDefRoll(int maxDefRoll) { this.maxDefRoll = maxDefRoll; }
    public int getMaxDefRoll() { return maxDefRoll; }

    public void setMaxAttRoll(int maxAttRoll) { this.maxAttRoll = maxAttRoll; }
    public int getMaxAttRoll() { return maxAttRoll; }

    public void setAccuracy(double accuracy) { this.accuracy = accuracy; }
    public double getAccuracy() { return accuracy; }

    public void setWin(int win) { this.win = win; }
    public int getWin() { return win; }

    public void setTotalDuels(int totalDuels) { this.totalDuels = totalDuels; }
    public int getTotalDuels() { return totalDuels; }

    public void setPercentOdds(double percentOdds) { this.percentOdds = percentOdds; }
    public double getPercentOdds() { return percentOdds; }

    public void setPidWin(int pidWin) { this.pidWin = pidWin; }
    public int getPidWin() { return pidWin; }

    public void setPid(boolean pid) {this.pid = pid; }
    public boolean getPid() { return pid; }

    public String toString() {
        NumberFormat f = NumberFormat.getPercentInstance();

        f.setMinimumFractionDigits(1);
        f.setMaximumFractionDigits(2);
        f.setMinimumIntegerDigits(1);
        f.setMaximumIntegerDigits(3);

        return ((Program.toCaps(getName()) + ": "
               + f.format(getPercentOdds())));

    }

    public void reset() {
        hp = getMaxHP();
        pid = false;
        attacks = 0;
        spec = 100;
    }


}
