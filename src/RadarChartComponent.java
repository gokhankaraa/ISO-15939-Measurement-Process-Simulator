import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.Map;

public class RadarChartComponent extends JComponent {
    private final Map<String, Double> dimensionScores;
    private final int ticks = 5;
    private final int padding = 50;

    public RadarChartComponent(Map<String, Double> dimensionScores) {
        this.dimensionScores = dimensionScores;
        setPreferredSize(new java.awt.Dimension(400, 400));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (dimensionScores == null || dimensionScores.size() < 3) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int size = Math.min(getWidth(), getHeight());
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        int maxRadius = (size / 2) - padding;

        int totalDimensions = dimensionScores.size();
        double angleStep = 2 * Math.PI / totalDimensions;

        drawGridAndAxes(g2, cx, cy, maxRadius, totalDimensions, angleStep);
        drawLabels(g2, cx, cy, maxRadius, totalDimensions, angleStep);
        drawDataPolygon(g2, cx, cy, maxRadius, totalDimensions, angleStep);
    }

    private void drawGridAndAxes(Graphics2D g2, int cx, int cy, int maxRadius, int numDims, double angleStep) {
        for (int i = 1; i <= ticks; i++) {
            double currentRadius = (double) i / ticks * maxRadius;
            Polygon gridPoly = new Polygon();
            for (int j = 0; j < numDims; j++) {
                double angle = j * angleStep - Math.PI / 2;
                int x = cx + (int) (currentRadius * Math.cos(angle));
                int y = cy + (int) (currentRadius * Math.sin(angle));
                gridPoly.addPoint(x, y);
            }
            g2.setColor(new Color(200, 200, 200));
            g2.drawPolygon(gridPoly);

            g2.setColor(Color.GRAY);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g2.drawString(String.valueOf(i), cx + 5, cy - (int)currentRadius + 5);
        }

        g2.setColor(new Color(150, 150, 150));
        for (int j = 0; j < numDims; j++) {
            double angle = j * angleStep - Math.PI / 2;
            int x = cx + (int) (maxRadius * Math.cos(angle));
            int y = cy + (int) (maxRadius * Math.sin(angle));
            g2.drawLine(cx, cy, x, y);
        }
    }

    private void drawLabels(Graphics2D g2, int cx, int cy, int maxRadius, int numDims, double angleStep) {
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 11));
        FontMetrics fm = g2.getFontMetrics();

        int i = 0;
        for (String dimName : dimensionScores.keySet()) {
            double angle = i * angleStep - Math.PI / 2;
            int labelRadius = maxRadius + 15;
            int x = cx + (int) (labelRadius * Math.cos(angle));
            int y = cy + (int) (labelRadius * Math.sin(angle));

            int labelWidth = fm.stringWidth(dimName);
            int labelHeight = fm.getHeight();

            if (angle > -Math.PI/2 - 0.1 && angle < -Math.PI/2 + 0.1) {
                x -= labelWidth / 2;
                y -= 5;
            } else if (angle > -0.1 && angle < 0.1) {
                y += labelHeight / 4;
            } else if (angle > Math.PI/2 - 0.1 && angle < Math.PI/2 + 0.1) {
                x -= labelWidth / 2;
                y += labelHeight;
            } else if (angle > Math.PI - 0.1 && angle < Math.PI + 0.1) {
                x -= labelWidth + 5;
                y += labelHeight / 4;
            } else {
                x -= labelWidth / 2;
                y += labelHeight / 4;
            }

            g2.drawString(dimName, x, y);
            i++;
        }
    }

    private void drawDataPolygon(Graphics2D g2, int cx, int cy, int maxRadius, int numDims, double angleStep) {
        Path2D.Double dataPath = new Path2D.Double();

        int i = 0;
        for (double score : dimensionScores.values()) {
            double normalizedScore = (score) / 5.0;
            double currentRadius = normalizedScore * maxRadius;

            double angle = i * angleStep - Math.PI / 2;
            double x = cx + currentRadius * Math.cos(angle);
            double y = cy + currentRadius * Math.sin(angle);

            if (i == 0) {
                dataPath.moveTo(x, y);
            } else {
                dataPath.lineTo(x, y);
            }
            i++;
        }
        dataPath.closePath();

        g2.setColor(new Color(100, 149, 237, 100));
        g2.fill(dataPath);

        g2.setStroke(new BasicStroke(2.0f));
        g2.setColor(new Color(100, 149, 237));
        g2.draw(dataPath);
    }
}