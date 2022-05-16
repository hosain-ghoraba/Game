package model.world;

import java.awt.Point;

import java.util.ArrayList;

import model.abilities.Ability;
import model.effects.Effect;
import model.effects.Root;
import model.effects.Stun;
import engine.Game;
import engine.Player;



public abstract class Champion implements Comparable,Damageable,Cloneable {
		
	private String name;
	private int maxHP ;
	private	int currentHP ;
	private	int mana; 
	private	int maxActionPointsPerTurn;
	private	int currentActionPoints ;
	private	int attackRange;
	private int attackDamage ;
	private	int speed;
	private  ArrayList<Ability> abilities;
	private ArrayList<Effect> appliedEffects;
	private Condition condition; 
	private Point location ;
	public Champion(String name, int maxHP, int mana, int maxActions, int speed, int attackRange,int attackDamage) {
		this.name=name;
		this.maxHP= maxHP;
		this.currentHP = maxHP;
		this.mana=mana ;
		this.maxActionPointsPerTurn =maxActions ;
		this.currentActionPoints = maxActions;
		this.speed=speed; 
		this.attackRange=attackRange;
		this.attackDamage=attackDamage ;
		abilities = new ArrayList<Ability>();
		appliedEffects = new ArrayList<Effect>();
		condition = Condition.ACTIVE ;
		
		
		
	}
	
////////////////// setters	
	
	public void setCurrentHP(int currentHP) {
		if(currentHP <= 0)	
		{
			this.currentHP = 0;
			this.condition = Condition.KNOCKEDOUT;
		}
		else if(currentHP > maxHP)
			this.currentHP = maxHP;
		else 
			this.currentHP = currentHP;
	}
	public void setMana(int mana) {
		this.mana = mana;
	}
	public void setMaxActionPointsPerTurn(int maxActionPointsPerTurn) {
		this.maxActionPointsPerTurn = maxActionPointsPerTurn;
	}
	public void setCurrentActionPoints(int currentActionPoints) {
		if(currentActionPoints < 0)
			this.currentActionPoints = 0;
		else if (currentActionPoints > maxActionPointsPerTurn)
			this.currentActionPoints = maxActionPointsPerTurn;
		else
		    this.currentActionPoints = currentActionPoints;
	}
	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	 // modified in M2 (Hosain)
	public void setCondition(Condition condition) { // Darwish !! : Very Important: the order of removing the effect from appliedEffects and setting the condition = ACTIVE is Fatal when using this method in remove method in stun class ( Darwish, Care PLS !)
// may need to be modified due to the stupid test cases!! they may make a champion having a condition without corrosponding exsisting effect in appliedEffects, the method won't work then.... :( !
/*		if(condition == Condition.KNOCKEDOUT ||this.condition == Condition.KNOCKEDOUT )
		    {
			this.condition = Condition.KNOCKEDOUT;
			return;
		    }		
        if(condition == Condition.INACTIVE)
		    {			
			this.condition = Condition.INACTIVE;
			return;
		    }
		if(condition == Condition.ROOTED)
            if(! Game.doesEffectExist(appliedEffects,"Stun"))
	            {
	            this.condition = Condition.ROOTED;
	            return;
	            }
		if(condition == Condition.ACTIVE)// will only happen in remove method in (Root) and (Stun) classes 
			{
			if(Game.doesEffectExist(appliedEffects,"Stun"))			
					return;				
			if (Game.doesEffectExist(appliedEffects,"Root"))
				{
					this.condition = Condition.ROOTED;
					return;
				}
			this.condition = Condition.ACTIVE;
					 
	        }		
	        */
		this.condition=condition;
	}
	public void setLocation(Point location) {
		this.location = location;
	}
	
///////////////////////////// getters

	
	public String getName() {
		return name;
	}
	public int getMaxHP() {
		return maxHP;
	}
	public int getCurrentHP() {
		return currentHP;
	}
	public int getMana() {
		return mana;
	}
	public int getMaxActionPointsPerTurn() {
		return maxActionPointsPerTurn;
	}
	public int getCurrentActionPoints() {
		return currentActionPoints;
	}
	public int getAttackRange() {
		return attackRange;
	}
	public int getAttackDamage() {
		return attackDamage;
	}
	public int getSpeed() {
		return speed;
	}
	public ArrayList<Ability> getAbilities() {
		return abilities;
	}
	public ArrayList<Effect> getAppliedEffects() {
		return appliedEffects;
	}
	public Condition getCondition() {
		return condition;
	}
	public Point getLocation() {
		return location;
	}
////////////////////////////////////////////////////////// end of getters
	
	/////// abstract method 
	//yousry edited.
	
	public abstract void useLeaderAbility(ArrayList<Champion> targets);
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub 
		Champion x =(Champion)o;
	if (speed > ((Champion)o).speed ) return -1;
	if (speed < ((Champion)o).speed ) return 1;
	else 	
     return -1 * x.name.compareTo(name) ; // dont know if should multiply the result by -1...he didn't clear this point, he only said : compare the champions names if their speeds are identical. we will know anyway in public tests     	
	}

	  

}
		
	