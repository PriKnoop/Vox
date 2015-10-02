package pojos;

import javax.xml.bind.annotation.XmlRootElement;

	@XmlRootElement
	public class Planet {
	    public int id;
	    public String name;
	    public double radius;
		public Planet(String name, double radius) {
			super();
			this.name = name;
			this.radius = radius;
		}
		public Planet() {
			super();
		}
	    
	    
	}


