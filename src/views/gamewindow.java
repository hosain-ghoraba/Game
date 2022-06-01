package views;
import model.world.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.concurrent.locks.Condition;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import engine.Game;
import engine.Player;

import model.world.Champion;
import model.*;
import model.abilities.Ability;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.effects.Effect;

public class gamewindow extends JFrame {
	JTextArea cur_champ_details ;
	JTextArea playersdetails ;
	Player pl1 ;
	Player pl2 ;
	 Game game ;
	 
	
	public gamewindow(Game game , Player pl1, Player pl2) {
		this.game =new Game(pl1,pl2) ;
		this.setVisible(true);
		this.pl1=pl1;
		this.pl2=pl2 ;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	//	this.setUndecorated(true);
	//	this.setResizable(false);
		ImageIcon logo = new ImageIcon("logo.png");
		setLayout(new GridLayout(6,6));
		this.setIconImage(logo.getImage());
		
		playersdetails = new JTextArea();
		playersdetails.setPreferredSize(new Dimension(150, getHeight()));

		playersdetails.setEditable(false);
		playersdetails.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		add(playersdetails);
updateplayersdata();
cur_champ_details =new JTextArea();
        

cur_champ_details.setEditable(false);
cur_champ_details.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
add(cur_champ_details);
update_curchamp_datails();
JPanel gamePanel =new JPanel();
gamePanel.setLayout(new GridLayout(5,5));
gamePanel.setSize(this.getWidth()-100,this.getHeight());


	}
	public void update_curchamp_datails() {
		Champion c = game.getCurrentChampion();
	//*	Their name.
		//Their type.
		//their currentHp, mana, and actions points.
		//Their list of abilities, as well as information about each ability. (Indicated below).
		//Their list of applied effects, as well as information. (Name and duration)
		//Their attack damage and attack range.
		
			
		String s = "current champion : "+c.getName() +"\n"+ "type: ";
		if(c instanceof AntiHero) {
			s+= "Anti hero" ;
		}
		else if(c instanceof Hero) {
			s+= " hero" ;

		}
		else
			s+= "villain" ;
		s+= "\n"+ "current Health points:  "+c.getCurrentHP() +"\n"+" mana"+ c.getMana() +"\n"+ "actions points :"+c.getCurrentActionPoints()+ "\n"+"abilities :";
		for(Ability x : c.getAbilities()) {
			s+="\n";
			s+=x.getName()+ " ,"  +"type: " ;
			if(x instanceof HealingAbility) {
				s+="HealingAbility"+ " ," +"mana: "+x.getManaCost()+ " ," +"action cost :"+x.getCastRange()+ " ," +"current cooldown: "+x.getCurrentCooldown()+ " ,"+" base cooldown:"+x.getBaseCooldown() + " ," ;
				s+= " heal amount :"+((HealingAbility)x).getHealAmount() ;
			}
			else if(x instanceof DamagingAbility) {
				s+= "DamagingAbility"+ " ," +"mana: "+x.getManaCost()+ " ," +"action cost :"+x.getCastRange()+ " ," +"current cooldown: "+x.getCurrentCooldown()+ " ,"+" base cooldown:"+x.getBaseCooldown() + " ," ;
				s+= " Damageamount:"+((DamagingAbility)x).getDamageAmount() ;
			}
			else {
				s+="CrowdControlAbility"+ " ," +"mana: "+x.getManaCost()+ " ," +"action cost :"+x.getCastRange()+ " ," +"current cooldown: "+x.getCurrentCooldown()+ " ,"+" base cooldown:"+x.getBaseCooldown() + " ," ;
				s+= " effect:"+ ((CrowdControlAbility)x).getEffect().getName() + "  effects's duration: "+((CrowdControlAbility)x).getEffect().getDuration() ;
			}
				//need to add info
			/*
			 * The ability name./*
The ability type.
The ability area of effect.
The ability cast range.
The mana and action costs.
The current and base cooldowns.
The healAmount/ damageAmount/ effect. (Depending on ability type).
			 */
		}
		s+="\n";
		for(Effect x :c.getAppliedEffects()) {
			
			s+= x.getName() + " duration :" + x.getDuration()+ "  ,";
			
		}
		s+="\n"+" attack damage :"+c.getAttackDamage()+"\n"+"attack range:"+ c.getAttackRange();
		
    cur_champ_details.setText(s);
		
	}
	//eeewww
	
public void updateplayersdata(){
		String s = pl1.getName() +" : "+"\n" + "remaining champions :";
		for(Champion x : pl1.getTeam()) {
			if(x.getCondition()!=(model.world.Condition.KNOCKEDOUT)) {
				s+= x.getName() + "  ";
			}
		}
		if(!game.isFirstLeaderAbilityUsed()) {
			s+="\n"+"  LeaderAbility not used";
		}
		else
			s+="\n"+"  LeaderAbility  used";
		String z = pl2.getName() +" : "+"\n" + "remaining champions :";
		for(Champion x : pl2.getTeam()) {
			if(x.getCondition()!=(model.world.Condition.KNOCKEDOUT)) {
				z+= x.getName() + "  ";
			}
		}
		if(!game.isSecondLeaderAbilityUsed()) {
			z+="\n"+"  LeaderAbility not used";
		}
		else
			z+="\n"+"  LeaderAbility  used";
playersdetails.setText(s +"\n"+z);
}
	
}
