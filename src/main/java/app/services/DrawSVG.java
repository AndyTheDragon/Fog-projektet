package app.services;

import java.util.Locale;

public class DrawSVG
{
    private static final String SVG_TEMPLATE = """
            <svg version="1.1"
                 x="%d" y="%d"
                 viewBox="%s"  width="%s" class="img-fluid"\s
                 preserveAspectRatio="xMinYMin">""";

    private static final String SVG_ARROW_DEFS = """
            <defs>
                <marker id="beginArrow" markerWidth="84" markerHeight="84" refX="0" refY="6" orient="auto">
                    <path d="M0,6 L12,0 L12,12 L0,6" style="fill: #000000;" />
                </marker>
                <marker id="endArrow" markerWidth="84" markerHeight="84" refX="12" refY="6" orient="auto">
                    <path d="M0,0 L12,6 L0,12 L0,0 " style="fill: #000000;" />
                </marker>
            </defs>""";

    private static final String SVG_RECT_TEMPLATE = "<rect x=\"%d\" y=\"%d\" height=\"%d\" width=\"%d\" style=\"%s\" />";
    private static final String SVG_SLANTED_RECT_TEMPLATE = "<rect x=\"%d\" y=\"%d\" height=\"%d\" width=\"%d\" transform=\"rotate(%f %d %d)\" style=\"%s\" />";
    private static final String SVG_LINE_TEMPLATE = "<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"%s\" />";
    private static final String SVG_TEXT_TEMPLATE = "<text style=\"text-anchor: middle;%s\" transform=\"translate(%d,%d) rotate(%d)\">%s</text>";

    private final StringBuilder svg = new StringBuilder();

    public DrawSVG(int x, int y, String viewBox, String width)
    {

        svg.append(String.format(Locale.ENGLISH, SVG_TEMPLATE, x, y, viewBox, width));
        svg.append(SVG_ARROW_DEFS);

    }

    public void addRectangle(int x, int y, int height, int width, String style)
    {
        svg.append(String.format(Locale.ENGLISH, SVG_RECT_TEMPLATE, x, y, height, width, style ));
    }

    public void addSlantedRect(int x, int y, int height, int width, double angle, int rotationX, int rotationY, String style)
    {
        svg.append(String.format(Locale.ENGLISH, SVG_SLANTED_RECT_TEMPLATE, x, y, height, width, angle, rotationX, rotationY, style ));
    }


    public void addLine(int x1, int y1, int x2, int y2, String style)
    {
        svg.append(String.format(Locale.ENGLISH, SVG_LINE_TEMPLATE, x1, y1, x2, y2, style));
    }

    public void addArrow(int x1, int y1, int x2, int y2, String style)
    {
        // Kald addLine med en style der indeholder pilehoveder
        addLine(x1, y1, x2, y2, "stroke:#000000; marker-start: url(#beginArrow); marker-end: url(#endArrow); " + style);
    }

    public void addText(int x, int y, int rotation, String text, String style)
    {
        svg.append(String.format(Locale.ENGLISH, SVG_TEXT_TEMPLATE, style, x, y, rotation, text));
    }

    public void addSvg(DrawSVG innerSvg)
    {
        svg.append(innerSvg.toString());
    }

    @Override
    public String toString()
    {
        return svg.append("</svg>").toString();
    }
}

