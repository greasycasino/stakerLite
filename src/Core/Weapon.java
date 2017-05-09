package Core;

public enum Weapon {
    Tentacle("Tentacle",0,90,0,86,1.0,4, 50),
    DScim("Dragon Scimitar",8,67,-2,66,1.0,4, 55),
    DDS("Dragon Dagger",40,25,-4,40,1.15,4, 25),
    NONE("No Weapon",0,0,0,0,1.0,4,0);

    Weapon(String weaponName, int stabBonus, int slashBonus, int crushBonus, int strBonus, double multiplier, int speed, int specCharge){
        this.weaponName = weaponName;
        this.stabBonus = stabBonus;
        this.slashBonus = slashBonus;
        this.crushBonus = crushBonus;
        this.strBonus = strBonus;
        this.multiplier = multiplier;
        this.speed = speed;
        this.specCharge = specCharge;
    }

    private final String weaponName;
    private final int stabBonus;
    private final int slashBonus;
    private final int crushBonus;
    private final int strBonus;
    private final double multiplier;
    private final int speed;
    private final int specCharge;

    public String getWeaponName(){
        return weaponName;
    }

    public int getStabBonus(){
        return stabBonus;
    }

    public int getSlashBonus(){
        return slashBonus;
    }

    public int getCrushBonus(){
        return crushBonus;
    }

    public int getStrBonus(){
        return strBonus;
    }

    public double getMultiplier(){return multiplier; }

    public int getSpeed(){
        return speed;
    }

    public int getSpecCharge() { return specCharge; }

}
