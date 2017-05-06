package objects;

//updated
import java.util.LinkedList;

public class NetSexOff {
	private LinkedList<NodeSexOff> network; 
	public NetSexOff(LinkedList<NodeSexOff> myNetwork){
		network = myNetwork;
	}
	public void addNodeSexOff(NodeSexOff n){
		network.add(n);
	}
	public void add(int i, NodeSexOff n){
		network.add(i, n);
	}
	public NodeSexOff get(int i){
		return network.get(i);
	}
	public NodeSexOff getLast(){
		return network.getLast();
	}
	public int size(){
		return network.size();
	}
	public void addAttackSetChild(int n1Index ,int n2Index, int valueAttack){
		//if(n1Index != n2Index)
		network.get(n1Index).getAttackSet().add(network.get(n2Index));
		network.get(n2Index).getAttackedBySet().add(network.get(n1Index));
		network.get(n1Index).addPower(valueAttack);
		network.get(n2Index).addHurt(valueAttack);

		network.get(n2Index).setLifeAfterAttack();

		if(network.get(n2Index).getLife() == 0){
			for(int i=0; i<network.get(n2Index).getAttackSet().size(); i++){
				if(network.get(n2Index).getAttackSetChildLife(i) > 0)
					network.get(n2Index).getAttackSet().get(i).setLifeAfterAttack();
			}
		}
	}
	public void addAttackSetChild(int n1Index, int valueAttack){
		network.get(n1Index).setLifeAfterAttack2(valueAttack);
		if(network.get(n1Index).getLife() == 0){
			for(int i=0; i<network.get(n1Index).getAttackSet().size(); i++){
				if(network.get(n1Index).getAttackSetChildLife(i) > 0)
					network.get(i).setLifeAfterAttack();
			}
		}
	}
	/*private boolean validation(int index){//keep these 2 methods for later
		int i;
		for(i = 0;(network.get(i).getPower(index) == 1 || network.get(i).getPower(index) == 0) ; i++){
			//see item 8 of definition 2.2
		}
		if(i == network.size()){
			return true;
		}	
		return false;
	}
	public boolean validate(){
		int i;
		for(i = 0; i < network.size() && validation(i) && network.get(i).getLife() <= 1;i++){
			//see item 8 of definition 2.2
		}
		if(i == network.size()){
			for(int j=0; j<network.size(); j++){
				if(network.get(j).getLife() != network.get(j).getLifeBefore())
					return false;
			}
		}	
		return true;
	}*/
}
