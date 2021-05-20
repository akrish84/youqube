package app.qube;

import java.util.ArrayList;
import java.util.List;


public class Qube {
	
	private List<Qube> queue;

	Qube() {
		queue = new ArrayList<>();
	}
	
	public void add(Qube item) {
		queue.add(item);
	}
	
	public void remove(Qube item) {
		queue.remove(item);
	}
	
	public Qube get(int idx) {
		return queue.get(idx);
	}

	public boolean hasIdx(int idx) {
		return idx < queue.size();
	}
}
