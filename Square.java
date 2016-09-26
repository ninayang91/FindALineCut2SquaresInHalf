

//Given two squares on a 2D plane, find a line segment that would cut these two squares in half.
//Assume that the top and the bottom sides of the square run parallel to x-axis
//The line segment returned should starts and ends at the edges of the squares
public class Square {
	double x1,x2,y1,y2;
	double size;
	public Square(double x1, double x2, double y1, double y2){
		this.x1=x1;
		this.x2=x2;
		this.y1=y1;
		this.y2=y2;
		size=Math.abs(x1-x2);
	}
	public Point middle(){
		return new Point((x1+x2)/2,(y1+y2)/2);
	}
	
	//Return the point where the line segment connecting mid1 and mid2 intercepts the edge of square 1
	//draw a line from mid2 to mid1,and continue it out until the edge of the square
	
	public Point extend(Point mid1, Point mid2, double size){
		//find what direction the line mid2 -> mid1 goes
		double xdir=mid1.x<mid2.x?-1:1;
		double ydir=mid1.y<mid2.y?-1:1;
		//if mid1.x==mid2.x, the slope calculation cannot be calculated, compute this specially
		if(mid1.x==mid2.x)return new Point(mid1.x,mid1.y+ydir*size/2.0);
		double k=(mid1.y-mid2.y)/(mid1.x-mid2.x);
		double x1=0,y1=0;
		if(Math.abs(k)==1){
			x1=mid1.x+xdir*size/2.0;
			y1=mid1.y+ydir*size/2.0;		
		}else if(Math.abs(k)<1){//shallow slope
			x1=mid1.x+xdir*size/2.0;
			y1=k*(x1-mid1.x)+mid1.y;
		}else{//steep slope
			y1=mid1.y+ydir*size/2.0;
			x1=(y1-mid1.y)/k+mid1.x;
		}
		
		return new Point(x1,y1);
	}
	
	//calculate where a line between each middle would collide with the edges of the square
	public Line cut(Square other){
		Point p1=extend(this.middle(),other.middle(),this.size);
		Point p2=extend(this.middle(),other.middle(),-1*this.size);
		Point p3=extend(other.middle(),this.middle(),other.size);
		Point p4=extend(other.middle(),this.middle(),-1*other.size);
		
		Point start=p1;Point end=p1;
		Point[] points={p2,p3,p4};
		
		for(int i=0;i<points.length;i++){
			if(points[i].x<start.x || (points[i].x==start.x && points[i].y<start.y)){
				start=points[i];
			}else if(points[i].x>end.x || (points[i].x==end.x && points[i].y>end.y)){
				end=points[i];
			}
		}
		return new Line(start, end);
	}

}
