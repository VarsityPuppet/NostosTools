/** This class represents a curve defined by a sequence of control points */
import java.awt.*;

public class ControlCurve {

	protected Polygon pts;
	protected int selection = -1;

	public ControlCurve() {
		pts = new Polygon();
	}

	static Font f = new Font("Courier", Font.PLAIN, 12);

	/** paint this curve into g. */
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		FontMetrics fm = g.getFontMetrics(f);
		g.setFont(f);
		int h = fm.getAscent() / 2;

		for (int i = 0; i < pts.npoints; i++) {
			String s = Integer.toString(i);
			int w = fm.stringWidth(s) / 2;
			g.drawString(Integer.toString(i), pts.xpoints[i] - w,
					pts.ypoints[i] + h);
			g.setColor(Color.BLUE);
			g.fillOval(pts.xpoints[i] - 10, pts.ypoints[i] - 10, 20, 20);
			g.setColor(Color.WHITE);
		}
	}

	static final int EPSILON = 15; /* square of distance for picking */

	/** return index of control point near to (x,y) or -1 if nothing near */
	public int selectPoint(int x, int y) {
		selection = -1;
		int closest = EPSILON;
		for (int i = 0; i < pts.npoints; i++) {
			int d = (int) Math.sqrt(Math.pow(pts.xpoints[i]-x,2) + Math.pow(pts.ypoints[i] - y,2) );
			if (d < EPSILON && d < closest) {
				closest = d;
				selection = i;
			}
		}
		return selection;
	}

	// square of an int
	static int sqr(double d) {
		return (int) (d * d);
	}

	/** add a control point, return index of new control point */
	public int addPoint(int x, int y) {
		pts.addPoint(x, y);
		return selection = pts.npoints - 1;
	}

	/** set selected control point */
	public void setPoint(int x, int y) {
		if (selection >= 0) {
			pts.xpoints[selection] = x;
			pts.ypoints[selection] = y;
		}
	}

	/** remove selected control point */
	public void removePoint() {
		if (selection >= 0) {
			pts.npoints--;
			for (int i = selection; i < pts.npoints; i++) {
				pts.xpoints[i] = pts.xpoints[i + 1];
				pts.ypoints[i] = pts.ypoints[i + 1];
			}
		}
	}

	public String toString() {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < pts.npoints; i++) {
			result.append(" " + pts.xpoints[i] + " " + pts.ypoints[i]);
		}
		return result.toString();
	}
}