package objects;

//updated
import java.util.LinkedList;
public class NodeSexOff {
	public final static int M = 1;
	private String name;
	private LinkedList<NodeSexOff> attackSet;
	private LinkedList<NodeSexOff> attackedBySet;
	private int initialLife;
	private int lifeBefore;
	private int life;
	private LinkedList<Integer> power;
	private LinkedList<Integer> hurt;
	private int attackHimSelf;
	public NodeSexOff(String myName, int myLife){
		name = myName;
		attackSet = new LinkedList<NodeSexOff>();
		attackedBySet = new LinkedList<NodeSexOff>();
		initialLife = myLife;
		lifeBefore = myLife;//for later use
		life = myLife;
		power = new LinkedList<Integer>();
		hurt = new LinkedList<Integer>();
		attackHimSelf = -1;
	}
	public String getName(){
		return name;
	}
	public  LinkedList<NodeSexOff> getAttackSet(){
		return attackSet;
	}
	public  LinkedList<NodeSexOff> getAttackedBySet(){
		return attackedBySet;
	}
	public  LinkedList<Integer> getHurt(){
		return hurt;
	}
	protected int getLifeBefore(){
		return lifeBefore;
	}
	public int getLife(){
		return life;
	}
	public int getPower(int i){
		return power.get(i);
	}
	public void addPower(int value){
		power.add(value);
	}
	public void addHurt(int value){
		hurt.add(value);
	}
	public void addPower(int index, int value){
		power.add(index, value);
	}
	public String getAttackSetFirstChildName(){
		return attackSet.getFirst().name;
	}
	public String getAttackSetChildName(int i){
		return attackSet.get(i).name;
	}
	public String getAttackedBySetFirstChildName(){
		return attackedBySet.getFirst().name;
	}
	public String getAttackedBySetChildName(int i){
		return attackedBySet.get(i).name;
	}
	public int getAttackSetFirstChildLife(){
		return attackSet.getFirst().life;
	}
	public int getAttackSetChildLife(int i){
		return attackSet.get(i).life;
	}
	public int getAttackedBySetFirstChildLife(){
		return attackedBySet.getFirst().life;
	}
	public int getAttackedBySetChildLife(int i){
		return attackedBySet.get(i).life;
	}
	public int beta(){
		int sum = 0;
		for(int i=0; i < attackedBySet.size(); i++){
			if(getAttackedBySetChildLife(i) > 0)
				sum ++;
		}
		if(attackHimSelf > 0)
			sum++;
		if(sum > M)
			return 1;
		return 0;
	}
	public int beta2(){
		int sum = 0;
		for(int i=0; i < attackedBySet.size(); i++){
			if(getAttackedBySetChildLife(i) > 0)
				sum ++;
		}
		if(sum +1 > M)
			return 1;
		return 0;
	}
	public int calcPart2(){
		int sum = 0;
		for(int i=0; i < attackedBySet.size(); i++){
			if(getAttackedBySetChildLife(i) > 0)
				sum += hurt.get(i);
		}
		if(attackHimSelf > 0)
			sum += attackHimSelf;
		return sum;
	}
	public void setLifeAfterAttack(){
		lifeBefore = life;
		if(life != 0){//n will not come back to life
			life = initialLife - (beta() + calcPart2());//see item 7 of definition 2.2 
		}
		if(life < 0)
			life = 0;
	}
	public void setLifeAfterAttack2(int hurt){
		lifeBefore = life;
		attackHimSelf = hurt;
		if(life != 0){//n will not come back to life
			life = life - (beta2() + hurt);//see item 7 of definition 2.2 
		}
		if(life < 0)
			life = 0;
	}
	public static void main(String[]args){
		NetSexOff network = new NetSexOff(new LinkedList<NodeSexOff>());//All the network
		network.addNodeSexOff(new NodeSexOff("David", 10));//add the sexOffenderNodes to the network
		network.addNodeSexOff(new NodeSexOff("Aharon", 8));//name: Aharon, Life: 8
		network.addNodeSexOff(new NodeSexOff("Jack", 9));
		network.addNodeSexOff(new NodeSexOff("Amy", 12));
		network.addAttackSetChild(0, 1, 2);//add an arrow from David to Aharon with power:2
		network.addAttackSetChild(0, 2, 4);//add an arrow from David to Jack with power:4
		System.out.println(network.get(0).getName()+"'s life:"+network.get(0).getLife());
		System.out.println(network.get(1).getName()+"'s life:"+network.get(1).getLife());
		System.out.println(network.get(2).getName()+"'s life:"+network.get(2).getLife());

		network.addAttackSetChild(0, 2);//add an arrow from David to David with power:2
		
		System.out.println(network.get(0).getName()+"'s life:"+network.get(0).getLife());
		System.out.println(network.get(1).getName()+"'s life:"+network.get(1).getLife());
		System.out.println(network.get(2).getName()+"'s life:"+network.get(2).getLife());
		
		network.addAttackSetChild(2, 0, 3);//add an arrow from 	Jack to David with power:3
		
	
		System.out.println(network.get(0).getName()+"'s life:"+network.get(0).getLife());
		System.out.println(network.get(1).getName()+"'s life:"+network.get(1).getLife());
		System.out.println(network.get(2).getName()+"'s life:"+network.get(2).getLife());
		System.out.println(network.get(3).getName()+"'s life:"+network.get(3).getLife());

		
		network.addAttackSetChild(1, 2, 3);//add an arrow from 	Aharon to Jack with power:3
		

		System.out.println(network.get(0).getName()+"'s life:"+network.get(0).getLife());
		System.out.println(network.get(1).getName()+"'s life:"+network.get(1).getLife());
		System.out.println(network.get(2).getName()+"'s life:"+network.get(2).getLife());
		System.out.println(network.get(3).getName()+"'s life:"+network.get(3).getLife());
		
		network.addAttackSetChild(3, 0, 5);//add an arrow from 	Amy to David with power:6
		System.out.println(network.get(0).getName()+"'s life:"+network.get(0).getLife());
		System.out.println(network.get(1).getName()+"'s life:"+network.get(1).getLife());
		System.out.println(network.get(2).getName()+"'s life:"+network.get(2).getLife());
		System.out.println(network.get(3).getName()+"'s life:"+network.get(3).getLife());

		
		network.addAttackSetChild(1, 3, 12);//add an arrow from Aharon to Amy with power:12
		
		System.out.println(network.get(0).getName()+"'s life:"+network.get(0).getLife());
		System.out.println(network.get(1).getName()+"'s life:"+network.get(1).getLife());
		System.out.println(network.get(2).getName()+"'s life:"+network.get(2).getLife());
		System.out.println(network.get(3).getName()+"'s life:"+network.get(3).getLife());
		
		network.addAttackSetChild(2, 1, 9);//add an arrow from 	Jack to Aharon with power:3
		
		System.out.println(network.get(0).getName()+"'s life:"+network.get(0).getLife());
		System.out.println(network.get(1).getName()+"'s life:"+network.get(1).getLife());
		System.out.println(network.get(2).getName()+"'s life:"+network.get(2).getLife());
		System.out.println(network.get(3).getName()+"'s life:"+network.get(3).getLife());
		System.out.println(network.validate());
	}
}
