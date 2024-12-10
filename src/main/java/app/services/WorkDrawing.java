package app.services;

import app.entities.Carport;

public class WorkDrawing
{
    private final DrawSVG topViewDrawing;
    private final DrawSVG sideViewDrawing;
    private final int carportWidth;
    private final int carportLength;
    private final int carportHeight;
    private final int shedWidth;
    private final int shedLength;
    private final boolean hasShed;
    private final int numberOfJoists;
    private final boolean extraPostsForLongCarport;
    private final static String STYLE="stroke:#000000; fill: #ffffff;";
    private final static String STYLE_DASHED="stroke:#000000; fill: #ffffff; stroke-dasharray: 15;";
    private final static String STYLE_ARROW="stroke-width: 8; fill: #ffffff; ";
    private final static String STYLE_TEXT = "font-size: 120px";
    private final static int fasciaThickness = 50;

    public WorkDrawing(Carport carport, int drawingWidth)
    {
        this.carportWidth = carport.getWidth() * 10;
        this.carportLength = carport.getLength() * 10;
        this.carportHeight = 230 * 10;
        this.shedWidth = carport.getShedWidth()*10;
        this.shedLength = carport.getShedLength()*10;
        this.hasShed = carport.hasShed();
        this.numberOfJoists = carport.getNumberOfJoists();
        this.extraPostsForLongCarport = carport.extraPostsForLongCarport();
        this.topViewDrawing = new DrawSVG(0, 0, "-1000 0 " + (this.carportLength + 2000) + " " + (this.carportWidth + 1000), "" + drawingWidth);
        this.sideViewDrawing = new DrawSVG(0,0,"-1000 0 " + (this.carportLength +2000) + " " + (this.carportHeight + 1000), ""+ drawingWidth);

        //Add width and length arrows
        int textOffset = 750;
        int arrowOffset = textOffset - 150;
        topViewDrawing.addArrow(-(arrowOffset),0, -600, this.carportWidth, STYLE_ARROW);
        topViewDrawing.addText(-textOffset,(this.carportWidth)/2, 0, ""+(this.carportWidth/10), STYLE_TEXT);
        topViewDrawing.addArrow(0, this.carportWidth+arrowOffset, this.carportLength, this.carportWidth+arrowOffset, STYLE_ARROW);
        topViewDrawing.addText(this.carportLength/2, this.carportWidth+textOffset, 0, ""+(this.carportLength/10), STYLE_TEXT);

        addBeams();
        addPosts();
        if (this.hasShed)
        {
            addCladding();
        }
        addFascia();
        addJoists();
    }

    private void addBeams()
    {
        int topY = fasciaThickness+350;
        int botY = fasciaThickness+carportWidth-350-45;
        int beamThickness = 45;
        topViewDrawing.addArrow(-200,topY+beamThickness, -200, botY, STYLE_ARROW);
        topViewDrawing.addText(-350,(botY+topY)/2, 0, ""+(botY-topY)/10, STYLE_TEXT);
        topViewDrawing.addRectangle(fasciaThickness,topY,beamThickness, carportLength-2*fasciaThickness, STYLE);
        topViewDrawing.addRectangle(fasciaThickness,botY,beamThickness, carportLength-2*fasciaThickness, STYLE);
        sideViewDrawing.addSlantedRect(fasciaThickness,250,145, carportLength-2*fasciaThickness, 0.735, fasciaThickness+carportLength/2, 250, STYLE);
    }

    private void addPosts()
    {
        // overstern + understern + 2xlægte - halv stolpe
        int postXoffset = 1000;
        int leftPostY = fasciaThickness+350-96/2;
        int rightPostY = fasciaThickness+ carportWidth -350-96/2;
        // Arrows

        //Front posts
        topViewDrawing.addRectangle(postXoffset,leftPostY,97, 97, STYLE);
        topViewDrawing.addRectangle(postXoffset,rightPostY,97, 97, STYLE);
        sideViewDrawing.addRectangle(postXoffset,leftPostY,2080,97, STYLE);
        int middlepostXoffset = 3300;
        if (!hasShed)
        {
            middlepostXoffset = Math.max((carportLength-postXoffset-300)/2,3300);
        }
        if (extraPostsForLongCarport)
        {
            // Middle posts
            topViewDrawing.addRectangle(postXoffset+middlepostXoffset,leftPostY,97, 97, STYLE);
            topViewDrawing.addRectangle(postXoffset+middlepostXoffset,rightPostY,97, 97, STYLE);
            sideViewDrawing.addRectangle(postXoffset+middlepostXoffset,fasciaThickness+350,2050,97, STYLE);
        }
        if (hasShed)
        {
            //Shed posts
            topViewDrawing.addRectangle(carportLength -300-shedLength,leftPostY,97, 97, STYLE);
            topViewDrawing.addRectangle(carportLength -300-shedLength,fasciaThickness+ carportWidth/2-96/2,97, 97, STYLE);
            topViewDrawing.addRectangle(carportLength -300-shedLength,rightPostY,97, 97, STYLE);
            //Back middle post
            topViewDrawing.addRectangle(carportLength -300-97,fasciaThickness+ carportWidth/2-96/2,97, 97, STYLE);

        }
        //Back posts
        topViewDrawing.addRectangle(carportLength -300-97,leftPostY,97, 97, STYLE);
        topViewDrawing.addRectangle(carportLength -300-97,rightPostY,97, 97, STYLE);
        if(!hasShed)
        {
            sideViewDrawing.addRectangle(carportLength - 300 - 97, fasciaThickness + 350, 2020, 97, STYLE);
        }


    }
    private void addJoists()
    {

        //int joistSpacing = carportLength/numberOfJoists-45-(2*45/numberOfJoists);
        int joistSpacing = (carportLength-(3*fasciaThickness/2)-45)/numberOfJoists;
        int sum = 0;
        double fallPercentage = 1.28/100;
        double totalFall = carportLength*fallPercentage;

        //while (sum < carportLength-2*fasciaThickness)
        for (int i = 1; i < numberOfJoists; i++)
        {
            int arrowY = carportWidth+250;
            int arrowX = fasciaThickness+sum+22;
            String joistSpacingString = String.format("%.1f", (float)(joistSpacing+45)/10);
            topViewDrawing.addArrow(arrowX, arrowY, arrowX+joistSpacing+45, arrowY, STYLE_ARROW);
            topViewDrawing.addText(arrowX+(joistSpacing/2), arrowY+130, 0, joistSpacingString, STYLE_TEXT);
            topViewDrawing.addRectangle(fasciaThickness+sum,fasciaThickness, carportWidth-2*fasciaThickness, 45, STYLE);

            double yOffSet = sum*fallPercentage;
            sideViewDrawing.addRectangle(fasciaThickness+10+sum,190 + (int)yOffSet,195, 45, STYLE_DASHED);

            sum += joistSpacing + 45;
        }
        topViewDrawing.addRectangle(carportLength-fasciaThickness/2-45, fasciaThickness, carportWidth-2*fasciaThickness, 45, STYLE);
        sideViewDrawing.addRectangle(carportLength-fasciaThickness-55, 270,195, 45, STYLE_DASHED);

    }

    private void addFascia()
    {
        topViewDrawing.addRectangle(fasciaThickness/2,0, carportWidth, fasciaThickness/2, STYLE);
        topViewDrawing.addRectangle(0,0, carportWidth, fasciaThickness/2, STYLE);
        //sides
        topViewDrawing.addRectangle(fasciaThickness,fasciaThickness/2, fasciaThickness/2, carportLength-fasciaThickness, STYLE);
        topViewDrawing.addRectangle(fasciaThickness,0, 25, carportLength-fasciaThickness, STYLE);
        topViewDrawing.addRectangle(fasciaThickness,carportWidth-fasciaThickness, fasciaThickness/2, carportLength-fasciaThickness, STYLE);
        topViewDrawing.addRectangle(fasciaThickness,carportWidth-fasciaThickness/2, fasciaThickness/2, carportLength-fasciaThickness, STYLE);
        sideViewDrawing.addSlantedRect(fasciaThickness,250,200, carportLength-2*fasciaThickness, 0.735, fasciaThickness+carportLength/2, 0, STYLE);
        sideViewDrawing.addSlantedRect(fasciaThickness,220,125, carportLength-2*fasciaThickness, 0.735, fasciaThickness+carportLength/2, 0, STYLE);
        //back
        //topViewDrawing.addRectangle(carportLength-fasciaThickness, fasciaThickness, carportWidth-2*fasciaThickness, fasciaThickness/2, STYLE);
        topViewDrawing.addRectangle(carportLength-fasciaThickness/2, fasciaThickness, carportWidth-2*fasciaThickness, fasciaThickness/2, STYLE);

    }

    private void addCladding()
    {
        int sum=0;
        while (sum < shedWidth-2*16)
        {
            //front
            topViewDrawing.addRectangle(carportLength -300-shedLength-16,fasciaThickness+350-96/2-16+sum,100, 16, STYLE);
            topViewDrawing.addRectangle(carportLength -300-shedLength-16-16,fasciaThickness+350-96/2-16+75+sum,100, 16, STYLE);
            //back
            topViewDrawing.addRectangle(carportLength -300,fasciaThickness+350-96/2-16+sum,100, 16, STYLE);
            topViewDrawing.addRectangle(carportLength -300+16,fasciaThickness+350-96/2-16+75+sum,100, 16, STYLE);

            sum += 150;
        }
        sum = 0;
        while (sum < shedLength-2*16)
        {
            //sides
            topViewDrawing.addRectangle(carportLength -300-shedLength+sum,fasciaThickness+350-96/2-16,16, 100, STYLE);
            topViewDrawing.addRectangle(carportLength -300-shedLength+75+sum,fasciaThickness+350-96/2-16-16,16, 100, STYLE);
            topViewDrawing.addRectangle(carportLength -300-shedLength+sum,carportWidth-350+97,16, 100, STYLE);
            topViewDrawing.addRectangle(carportLength -300-shedLength+75+sum,carportWidth-350+97+16,16, 100, STYLE);
            sideViewDrawing.addRectangle(carportLength -300-shedLength+sum,fasciaThickness+350,2000, 100, STYLE);
            sideViewDrawing.addRectangle(carportLength -300-shedLength+75+sum,fasciaThickness+350-8,2000, 100, STYLE);

            sum += 150;
        }

    }

    public String toString()
    {
        return topViewDrawing.toString() + sideView();

    }

    public String sideView()
    {
        return sideViewDrawing.toString();
    }
}
