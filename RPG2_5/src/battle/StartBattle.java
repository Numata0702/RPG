package battle;

import livingThings.Hero;
import livingThings.Origin;

public class StartBattle {

	public static String messages( Hero h,Origin o) {

		
			h.attack(o);
		
		return "テストです";
	}
}
