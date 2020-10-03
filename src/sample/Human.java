package sample;

public class Human {
		String name,workspace,home,phonenumber;
		boolean isMaskOn;
		int IllLevel;
		//Place placeinfo;

		public Human(){
			this(null,null,null,null,false,0);
		}
		public Human(String a,String b,String c,String d,boolean isMaskOn,int IllLevel) {
			name=a;workspace=b;home=c;phonenumber=d;this.isMaskOn=isMaskOn;this.IllLevel=IllLevel;
		}
		public boolean isEqual(Human a){
			if(this.phonenumber.equals(a.phonenumber))
				return true;
			else return false;
		}

		public String toString(){
			return name+" "+workspace+" "+home+" "+phonenumber+" "+isMaskOn+" "+IllLevel;
		}
}
