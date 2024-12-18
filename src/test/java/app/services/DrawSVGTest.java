package app.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrawSVGTest
{

    @BeforeEach
    void setUp()
    {
    }

    @Test
    void testSwedishFlag()
    {
        //Arrange
        String expected = """
                <svg version="1.1"
                     x="0" y="0"
                     viewBox="0 0 255 210"  width="127" class="img-fluid"\s
                     preserveAspectRatio="xMinYMin">\
                <defs>
                    <marker id="beginArrow" markerWidth="84" markerHeight="84" refX="0" refY="6" orient="auto">
                        <path d="M0,6 L12,0 L12,12 L0,6" style="fill: #000000;" />
                    </marker>
                    <marker id="endArrow" markerWidth="84" markerHeight="84" refX="12" refY="6" orient="auto">
                        <path d="M0,0 L12,6 L0,12 L0,0 " style="fill: #000000;" />
                    </marker>
                </defs>\
                <rect x="0" y="0" height="90" width="90" style="stroke:#6dab6e; fill: #6d6fab" />\
                <rect x="120" y="0" height="90" width="135" style="stroke:#000000; fill: #ff0000" />\
                <rect x="0" y="120" height="90" width="90" style="stroke:#000000; fill: #ff0000" />\
                <rect x="120" y="120" height="90" width="135" style="stroke:#000000; fill: #ff0000" />\
                </svg>""";

        //Act
        DrawSVG actual = new DrawSVG(0, 0, "0 0 255 210", "127");
        actual.addRectangle(0,0,90,90,"stroke:#6dab6e; fill: #6d6fab");
        actual.addRectangle(120,0,90,135,"stroke:#000000; fill: #ff0000");
        actual.addRectangle(0,120,90,90,"stroke:#000000; fill: #ff0000");
        actual.addRectangle(120,120,90,135,"stroke:#000000; fill: #ff0000");
        //Assert
        assertEquals(expected, actual.toString());
    }

    @Test
    void testArrows()
    {
        //Arrange
        String expected = """
<svg version="1.1"
     x="0" y="0"
     viewBox="0 0 255 210"  width="127" class="img-fluid"\s
     preserveAspectRatio="xMinYMin">\
<defs>
    <marker id="beginArrow" markerWidth="84" markerHeight="84" refX="0" refY="6" orient="auto">
        <path d="M0,6 L12,0 L12,12 L0,6" style="fill: #000000;" />
    </marker>
    <marker id="endArrow" markerWidth="84" markerHeight="84" refX="12" refY="6" orient="auto">
        <path d="M0,0 L12,6 L0,12 L0,0 " style="fill: #000000;" />
    </marker>
</defs>\
<line x1="0" y1="200" x2="250" y2="200" style="stroke:#000000; marker-start: url(#beginArrow); marker-end: url(#endArrow); " />\
</svg>""";

        //Act
        DrawSVG actual = new DrawSVG(0, 0, "0 0 255 210", "127");
        actual.addArrow(0,200,250,200,"");

        //Assert
        assertEquals(expected, actual.toString());

    }
}