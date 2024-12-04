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
    private final static String STYLE_DASHED="stroke:#000000; fill: #ffffff; stroke-dasharray: 5,5;";
    private final static int fasciaThickness = 50;

   public WorkDrawing(int carportLength, int carportWidth, int carportHeight, int shedLength, int shedWidth, int numberOfJoists, boolean extraPostsForLongCarport)
    {
        this.carportWidth = carportWidth * 10;
        this.carportLength = carportLength * 10;
        this.carportHeight = carportHeight * 10;
        this.shedWidth = shedWidth*10;
        this.shedLength = shedLength*10;
        this.hasShed = true;
        this.numberOfJoists = numberOfJoists;
        this.extraPostsForLongCarport = extraPostsForLongCarport;
        this.topViewDrawing = new DrawSVG(0, 0, "0 0 " + (this.carportLength + 100) + " " + (this.carportWidth + 100), "" + carportLength);
        this.sideViewDrawing = new DrawSVG(0,0,"0 0 " + (this.carportLength +100) + " " + (this.carportHeight +100), ""+ carportLength);
        addBeams();
        addPosts();
        addCladding();
        addFascia();
        addJoists();
    }
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
        this.topViewDrawing = new DrawSVG(0, 0, "0 0 " + (this.carportLength + 100) + " " + (this.carportWidth + 100), "" + drawingWidth);
        this.sideViewDrawing = new DrawSVG(0,0,"0 0 " + (this.carportLength +100) + " " + (this.carportHeight + 400), ""+ drawingWidth);
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
        topViewDrawing.addRectangle(fasciaThickness,fasciaThickness+350,45, carportLength-2*fasciaThickness, STYLE);
        topViewDrawing.addRectangle(fasciaThickness,fasciaThickness+carportWidth-350-45,45, carportLength-2*fasciaThickness, STYLE);
        sideViewDrawing.addSlantedRect(fasciaThickness,250,145, carportLength-2*fasciaThickness, 0.735, fasciaThickness+carportLength/2, 250, STYLE);
    }

    private void addPosts()
    {
        // overstern + understern + 2xlægte - halv stolpe
        int postXoffset = 1000;
        //Front posts
        topViewDrawing.addRectangle(postXoffset,fasciaThickness+350-96/2,97, 97, STYLE);
        topViewDrawing.addRectangle(postXoffset,fasciaThickness+ carportWidth -350-96/2,97, 97, STYLE);
        sideViewDrawing.addRectangle(postXoffset,fasciaThickness+350-96/2,2080,97, STYLE);
        int middlepostXoffset = 3300;
        if (!hasShed)
        {
            middlepostXoffset = Math.max((carportLength-postXoffset-300)/2,3300);
        }
        if (extraPostsForLongCarport)
        {
            // Middle posts
            topViewDrawing.addRectangle(postXoffset+middlepostXoffset,fasciaThickness+350-96/2,97, 97, STYLE);
            topViewDrawing.addRectangle(postXoffset+middlepostXoffset,fasciaThickness+ carportWidth -350-96/2,97, 97, STYLE);
            sideViewDrawing.addRectangle(postXoffset+middlepostXoffset,fasciaThickness+350,2050,97, STYLE);
        }
        if (hasShed)
        {
            //Shed posts
            topViewDrawing.addRectangle(carportLength -300-shedLength,fasciaThickness+350-96/2,97, 97, STYLE);
            topViewDrawing.addRectangle(carportLength -300-shedLength,fasciaThickness+ carportWidth/2-96/2,97, 97, STYLE);
            topViewDrawing.addRectangle(carportLength -300-shedLength,fasciaThickness+ carportWidth -350-96/2,97, 97, STYLE);
            //Back middle post
            topViewDrawing.addRectangle(carportLength -300-97,fasciaThickness+ carportWidth/2-96/2,97, 97, STYLE);

        }
        //Back posts
        topViewDrawing.addRectangle(carportLength -300-97,fasciaThickness+350-96/2,97, 97, STYLE);
        topViewDrawing.addRectangle(carportLength -300-97,fasciaThickness+ carportWidth -350-96/2,97, 97, STYLE);
        if(!hasShed)
        {
            sideViewDrawing.addRectangle(carportLength - 300 - 97, fasciaThickness + 350, 2020, 97, STYLE);
        }


    }
    private void addJoists()
    {

        int joistSpacing = carportLength/numberOfJoists-45-(2*45/numberOfJoists);
        int sum = 0;
        double fallPercentage = 1.28/100;
        double totalFall = carportLength*fallPercentage;

        while (sum < carportLength-2*fasciaThickness)
        {

            topViewDrawing.addRectangle(fasciaThickness+sum,fasciaThickness, carportWidth-2*fasciaThickness, 45, STYLE);
            for (int i=0; i < numberOfJoists; i++)
            {
                double yOffSet = sum*fallPercentage;
            sideViewDrawing.addRectangle(fasciaThickness+10+sum,190 + (int)yOffSet,195, 45, STYLE_DASHED);
            }
            sum += joistSpacing + 45;
        }
        topViewDrawing.addRectangle(carportLength-fasciaThickness-45, fasciaThickness, carportWidth-2*fasciaThickness, 45, STYLE);
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
        topViewDrawing.addRectangle(carportLength-fasciaThickness, fasciaThickness, carportWidth-2*fasciaThickness, fasciaThickness/2, STYLE);
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
