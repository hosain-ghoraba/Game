package views;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import engine.Game;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.GameActionException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.world.Champion;
import model.world.Cover;
import model.world.Damageable;
import model.world.Direction;

public class marvelcontroler implements ActionListener ,KeyListener{


	private gamewindow view ;

	private gamewindow w ;

	private JButton [][] boardJbutton ;
	private Game game;
	Boolean isSingleTargerAbilityCasted ;
	Ability singleTargetAbility ;
	
	 public marvelcontroler (Game game)
	{
		isSingleTargerAbilityCasted = false ;
		this.game = game;		
		System.out.println(game.getFirstPlayer().getLeader().getName());
		
		boardJbutton = new JButton [5][5] ;
		view = new gamewindow(game);
		view.addKeyListener(this);
		view.gamePanel.addKeyListener(this);
		view.setFocusable(true);
		view.gamePanel.setFocusable(true);
		for (int i=4;i>=0;i--)
		{
			for (int j=0;j<5;j++)
			{
			
				JButton x = new JButton ();
				
			x.addActionListener(this);
			boardJbutton[i][j]=x;
			if(game.getBoard()[i][j]!=null) {
				Object z =game.getBoard()[i][j];
				if(z instanceof Cover) {
				Cover c = (Cover)z ;
					
					x.setText("cover "+ " current healthpoints: "+c.getCurrentHP() );	
				
				}
				else {
				Champion c =(Champion) z;
				x.setText(c.getName() +" current healthpoints: "+c.getCurrentHP());
				  	
				}
			
			}
			view.add_button(x);
		
			
			}
		}

	}
	

		public void updateboard() {
			for(int i=4 ;i>=0;i--) {
				for(int j=0;j<5;j++) {
					if(game.getBoard()[i][j]==null)
						boardJbutton[i][j].setText("")
						;
					else {
						if(game.getBoard()[i][j] instanceof Champion) {
							boardJbutton[i][j].setText(((Champion)game.getBoard()[i][j]).getName()+  "  current health points:"+ ((Champion)game.getBoard()[i][j]).getCurrentHP());
							//need to add champion details in hover
							

						}
						else
						{
							
							boardJbutton[i][j].setText("cover , current health pionts:" +((Cover)game.getBoard()[i][j]).getCurrentHP() );
							
						}
						
					}
					
				}
			}
			
		}
        public void updateInfoPanel() {
        	view.infoPanel.setVisible(false);
        	view.infoPanel = view.give_updated_infoPanel(game);
        }
		
		private void attack(int response) {
			
			Direction attakeDirection = null;			
			switch(response) 
			{
			
			case(0): attakeDirection = Direction.RIGHT;  break;
			case(1): attakeDirection = Direction.LEFT ; break;					
			case(2): attakeDirection = Direction.UP ; break;                  			
			case(3): attakeDirection = Direction.DOWN ; break;
			
			default: //user closed the window without choosing
			{							
				
				while(response == -1) 
				{
					String[] options = new String[] {"Right", "left", "up", "down"};					
					response = JOptionPane.showOptionDialog(null, "you didn't choose the direction of the attack, please choose the direction of the attack", "attack",
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[0]);
				}
				attack(response);
				return;
				
			}
			
			}
			
			try
			{
				game.attack(attakeDirection);
			}
			catch (GameActionException e) 
			{
				JOptionPane.showMessageDialog(null, e.getMessage() ,"", JOptionPane.ERROR_MESSAGE);	
			}

			
			
}
		
		private void castabilt(Ability a)  {
			// TODO Auto-generated method stub
			if(a.getCastArea()==AreaOfEffect.SELFTARGET ||a.getCastArea()==AreaOfEffect.TEAMTARGET   ||a.getCastArea()==AreaOfEffect.SURROUND) {
				try
			{game.castAbility(a);}
			catch(GameActionException  e) {
				JOptionPane.showMessageDialog(null, e.getMessage(),"", JOptionPane.ERROR_MESSAGE);

				
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
			
			else if(a.getCastArea()==AreaOfEffect.DIRECTIONAL) {
	    		String[] options = new String[] {"Right", "left", "up", "down"};

				int response = JOptionPane.showOptionDialog(null, "choose the direction of the ability", "ability",
				            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
				            null, options, options[0]);
		while(response==-1) {
				switch(response) {
			case(0):
				try{game.castAbility(a, Direction.RIGHT);}
			catch(GameActionException  e) {
				JOptionPane.showMessageDialog(null, e.getMessage(),"", JOptionPane.ERROR_MESSAGE);

				
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			break ;
			case(1):
				try{game.castAbility(a, Direction.LEFT);}
			catch(GameActionException  e) {
				JOptionPane.showMessageDialog(null, e.getMessage(),"", JOptionPane.ERROR_MESSAGE);

				
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			break ;
			case(2):
				try{game.castAbility(a, Direction.UP);}
			catch(GameActionException  e) {
				JOptionPane.showMessageDialog(null, e.getMessage(),"", JOptionPane.ERROR_MESSAGE);

				
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			break ;
			case(3):
				try{game.castAbility(a, Direction.DOWN);}
			catch(GameActionException  e) {
				JOptionPane.showMessageDialog(null, e.getMessage(),"", JOptionPane.ERROR_MESSAGE);

				
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			break ;
			 default: //user closed the window without choosing
				 
			    		

					  response = JOptionPane.showOptionDialog(null, "you didn't choose the direction of the ability, please choose the direction of the ability", "ability",
					            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
					            null, options, options[0]);
					  break ;
				  }
				
		
		
		}}
			
			else {
				isSingleTargerAbilityCasted =true ;
				singleTargetAbility = a ;
				JOptionPane.showMessageDialog(null, " please choose who do you want to cast this ability on","", JOptionPane.INFORMATION_MESSAGE);
			}	
			/*	q =new JFrame() ;
			q.setVisible(true);
JLabel p =new JLabel();
p.setLayout(new GridLayout(5, 5));;
q.add(p);

			w.setVisible(false);
 for (int i=4;i>=0;i--)
	{
		for (int j=0;j<5;j++)
		{
	JButton x =	boardJbutton[i][j];
x.addActionListener(this);
p.add(x);
		}
			
	
		
		}
	}
*/
		}
			
			
			
		
		
		public int[] getindex (JButton x ) {
		 for(int i = 0;i<5;i++) {
			 for(int j = 0;j<5;j++)
				 if(boardJbutton[i][j]!=null ) {
					 if(boardJbutton[i][j]==x){
						 return new int [] {i,j} ;
					 }
				 }
			 
		 }
	 return null ;
	 
	 }
	


	@Override
	public void actionPerformed(ActionEvent e) {
    
	if(isSingleTargerAbilityCasted == false) 
		return ;
	isSingleTargerAbilityCasted = false ;
	JButton clicked = (JButton) e.getSource();
	//remaining code goes here 
	int[] b = getindex(clicked) ;
	try
	{		
		game.castAbility(singleTargetAbility , ((Damageable )game.getBoard()[b[0]][b[1]]).getLocation().x,  ((Damageable )game.getBoard()[b[0]][b[1]]).getLocation().y);
	}
	catch(GameActionException exp) 
	{
		JOptionPane.showMessageDialog(null, exp.getMessage(),"", JOptionPane.ERROR_MESSAGE);
	
	} catch (CloneNotSupportedException e1) {
		e1.printStackTrace();
	}
w.revalidate();
w.repaint();
	//q.setVisible(false);
	//w.setVisible(true);
	
	
	}

	@Override
	public void keyTyped(KeyEvent e) {
//System.out.println(e.getKeyCode());
	

	
}
	
	



	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
//		System.out.println(e.getExtendedKeyCode());
		System.out.println(e.getKeyCode());
		switch(e.getKeyCode()) {
		case(37) :
			try {
				game.move(Direction.LEFT);
		       		//boardJbutton[game.getCurrentChampion().getLocation().x][game.getCurrentChampion().getLocation().y].setText(game.getCurrentChampion().getName());
		       		//boardJbutton[game.getCurrentChampion().getLocation().x-1][game.getCurrentChampion().getLocation().y].setText("");
				updateboard();
				 JPanel z=  view.give_updated_infoPanel(game);
					view.remove(view.infoPanel);
					view.add(z,BorderLayout.EAST);
view.revalidate();
view.repaint();
			}
		   catch (GameActionException z) {
			   
				JOptionPane.showMessageDialog(null, z.getMessage(),"", JOptionPane.ERROR_MESSAGE);

			   
		   }  
		break;
		case(39) :
			try {
				game.move(Direction.RIGHT);
		       		//boardJbutton[game.getCurrentChampion().getLocation().x][game.getCurrentChampion().getLocation().y].setText(game.getCurrentChampion().getName());
		       		//boardJbutton[game.getCurrentChampion().getLocation().x-1][game.getCurrentChampion().getLocation().y].setText("");
				updateboard();
				 JPanel z=  view.give_updated_infoPanel(game);
					view.remove(view.infoPanel);
					view.add(z,BorderLayout.EAST);
view.revalidate();
view.repaint();
			}
		   catch (GameActionException z) {
			   
				JOptionPane.showMessageDialog(null, z.getMessage(),"", JOptionPane.ERROR_MESSAGE);

			   
		   }  
		break;

		case(38) :
			try {
				game.move(Direction.UP );
		       		//boardJbutton[game.getCurrentChampion().getLocation().x][game.getCurrentChampion().getLocation().y].setText(game.getCurrentChampion().getName());
		       		//boardJbutton[game.getCurrentChampion().getLocation().x-1][game.getCurrentChampion().getLocation().y].setText("");
				updateboard();
				 JPanel z=  view.give_updated_infoPanel(game);
					view.remove(view.infoPanel);
					view.add(z,BorderLayout.EAST);
view.revalidate();
view.repaint();
			}
		   catch (GameActionException z) {
			   
				JOptionPane.showMessageDialog(null, z.getMessage(),"", JOptionPane.ERROR_MESSAGE);

			   
		   }  
		break;
		case(40) :	try {
			game.move(Direction.DOWN );
       		//boardJbutton[game.getCurrentChampion().getLocation().x][game.getCurrentChampion().getLocation().y].setText(game.getCurrentChampion().getName());
       		//boardJbutton[game.getCurrentChampion().getLocation().x-1][game.getCurrentChampion().getLocation().y].setText("");
		updateboard();
		 JPanel z=  view.give_updated_infoPanel(game);
			view.remove(view.infoPanel);
			view.add(z,BorderLayout.EAST);
			view.revalidate();
			view.repaint();
	}
   catch (GameActionException z) {
	   
		JOptionPane.showMessageDialog(null, z.getMessage(),"", JOptionPane.ERROR_MESSAGE);

	   
   }  
break;
	}
		
	
		switch(e.getKeyChar()) {
		
    	case('w'):   //leader ability 
		try {
			game.useLeaderAbility();
			//  w.updateplayersdata();
	            updateboard(); 
	            JPanel z=  view.give_updated_infoPanel(game);
		view.remove(view.infoPanel);
		view.add(z,BorderLayout.EAST);
		view.revalidate();
		view.repaint();
		
		}
	catch(GameActionException  x) {
		JOptionPane.showMessageDialog(null, x.getMessage(),"", JOptionPane.ERROR_MESSAGE);

		
	} 
    	break;
    	case('a')://attack
    		String[] options = new String[] {"Right", "left", "up", "down"};
        int response = JOptionPane.showOptionDialog(null, "choose the direction of the attack", "attack",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
         attack(response);  
      //   w.updateplayersdata();
            updateboard();
//            w.give_updated_infoPanel(game);
            JPanel z=  view.give_updated_infoPanel(game);
    		view.remove(view.infoPanel);
    		view.add(z,BorderLayout.EAST);
    		view.revalidate();
    		view.repaint();
            //need to update the board 
         //need to check if game is over
         break ;
    	case('e'): //end turn 
    	
    			game.endTurn();
    		//	w.update_curchamp_datails();
    		//	w.updateplayersdata();
    		     updateboard();
    		    view.remove(view.infoPanel); 	        
    		    view.add(view.give_updated_infoPanel(game),BorderLayout.EAST);
    		    view.revalidate();
    			view.repaint();
    		     //need to update the board
    	         //need to check if game is over
break ;
    	case ('c'): //casting an ability 
             ArrayList<Ability> opt = new ArrayList<>() ;
    	    for(Ability h : game.getCurrentChampion().getAbilities()) {
    	    	if (h.getCurrentCooldown()==0)
    	    		opt.add(h);
    	    	
    	    }
    		String[] options2 = new String[opt.size()];
    		 for(int i =0;i<opt.size();i++) {
    			 
    			 options2[i]= opt.get(i).getName();
    			 
    		 }
    		  int response2 = JOptionPane.showOptionDialog(null, "which ability do you want to cast", "ability",
    		            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
    		            null, options2, options2[0]);
    			castabilt(opt.get(response2));
    	//		w.updateplayersdata();
   		     updateboard();
   		  view.remove(view.infoPanel); 	        
		    view.add(view.give_updated_infoPanel(game),BorderLayout.EAST);

		    view.revalidate();
			view.repaint();
    	    
    	    
    	    
    		          		
    	
default: break ;
}
	
	}
	
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(e.getKeyChar());

	}
	

   public static void main(String[] args) {
//	while(true) {
new loginWindow()
;



}
}