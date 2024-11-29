package app.services;

public class WorkDrawing
{
    private final DrawSVG svgDrawing;
    private final int carportWidth;
    private final int carportLength;
    private final int carportHeight;
    private final int shedWidth;
    private final int shedLength;
    private boolean hasShed;
    private final static String STYLE="stroke:#000000; fill: #ffffff;";
    private final static int fasciaThickness = 50;

    public WorkDrawing(int carportWidth, int carportLength, int carportHeight)
    {
        this.carportWidth = carportWidth *10;
        this.carportLength = carportLength *10;
        this.carportHeight = carportHeight *10;
        this.shedWidth = 0;
        this.shedLength = 0;
        this.hasShed = false;
        this.svgDrawing = new DrawSVG(0,0,"0 0 " + (this.carportLength +100) + " " + (this.carportWidth +100), ""+ carportLength);
        addBeams();
        addJoists();
        addPosts();
        addFascia();
    }

    public WorkDrawing(int carportWidth, int carportLength, int carportHeight, int shedWidth, int shedLength)
    {
        this.carportWidth = carportWidth * 10;
        this.carportLength = carportLength * 10;
        this.carportHeight = carportHeight * 10;
        this.shedWidth = shedWidth*10;
        this.shedLength = shedLength*10;
        this.hasShed = true;
        this.svgDrawing = new DrawSVG(0, 0, "0 0 " + (this.carportLength + 100) + " " + (this.carportWidth + 100), "" + carportLength);
        addBeams();
        addJoists();
        addPosts();
        addFascia();
        addCladding();
    }

    private void addBeams()
    {
        svgDrawing.addRectangle(fasciaThickness,fasciaThickness+350,45, carportLength-2*fasciaThickness, STYLE);
        svgDrawing.addRectangle(fasciaThickness,fasciaThickness+carportWidth-350-45,45, carportLength-2*fasciaThickness, STYLE);
    }

    private void addPosts()
    {
        // overstern + understern + 2xlægte - halv stolpe
        int postXoffset = 1000;
        //Front posts
        svgDrawing.addRectangle(postXoffset,fasciaThickness+350-96/2,97, 97, STYLE);
        svgDrawing.addRectangle(postXoffset,fasciaThickness+ carportWidth -350-96/2,97, 97, STYLE);
        int middlepostXoffset = 3300;
        if (!hasShed)
        {
            middlepostXoffset = Math.max((carportLength-postXoffset-300)/2,3300);
        }
        // Middle posts
        svgDrawing.addRectangle(postXoffset+middlepostXoffset,fasciaThickness+350-96/2,97, 97, STYLE);
        svgDrawing.addRectangle(postXoffset+middlepostXoffset,fasciaThickness+ carportWidth -350-96/2,97, 97, STYLE);
        if (hasShed)
        {
            //Shed posts
            svgDrawing.addRectangle(carportLength -300-shedLength,fasciaThickness+350-96/2,97, 97, STYLE);
            svgDrawing.addRectangle(carportLength -300-shedLength,fasciaThickness+ carportWidth/2-96/2,97, 97, STYLE);
            svgDrawing.addRectangle(carportLength -300-shedLength,fasciaThickness+ carportWidth -350-96/2,97, 97, STYLE);
            //Back middle post
            svgDrawing.addRectangle(carportLength -300-97,fasciaThickness+ carportWidth/2-96/2,97, 97, STYLE);

        }
        //Back posts
        svgDrawing.addRectangle(carportLength -300-97,fasciaThickness+350-96/2,97, 97, STYLE);
        svgDrawing.addRectangle(carportLength -300-97,fasciaThickness+ carportWidth -350-96/2,97, 97, STYLE);



    }
    private void addJoists()
    {

        int joistSpacing = 500;
        int sum = 0;
        while (sum < carportLength-2*fasciaThickness)
        {
            svgDrawing.addRectangle(fasciaThickness+sum,fasciaThickness, carportWidth-2*fasciaThickness, 45, STYLE);
            sum += joistSpacing + 50;
        }
        svgDrawing.addRectangle(carportLength-fasciaThickness-45,fasciaThickness, carportWidth-2*fasciaThickness, 45, STYLE);

    }

    private void addFascia()
    {
        svgDrawing.addRectangle(fasciaThickness/2,0, carportWidth, fasciaThickness/2, STYLE);
        svgDrawing.addRectangle(0,0, carportWidth, fasciaThickness/2, STYLE);
        //sides
        svgDrawing.addRectangle(fasciaThickness,fasciaThickness/2, fasciaThickness/2, carportLength-fasciaThickness, STYLE);
        svgDrawing.addRectangle(fasciaThickness,0, 25, carportLength-fasciaThickness, STYLE);
        svgDrawing.addRectangle(fasciaThickness,carportWidth-fasciaThickness, fasciaThickness/2, carportLength-fasciaThickness, STYLE);
        svgDrawing.addRectangle(fasciaThickness,carportWidth-fasciaThickness/2, fasciaThickness/2, carportLength-fasciaThickness, STYLE);
        //back
        svgDrawing.addRectangle(carportLength-fasciaThickness, fasciaThickness, carportWidth-2*fasciaThickness, fasciaThickness/2, STYLE);
        svgDrawing.addRectangle(carportLength-fasciaThickness/2, fasciaThickness, carportWidth-2*fasciaThickness, fasciaThickness/2, STYLE);

    }

    private void addCladding()
    {
        int sum=0;
        while (sum < shedWidth-2*16)
        {
            //front
            svgDrawing.addRectangle(carportLength -300-shedLength-16,fasciaThickness+350-96/2-16+sum,100, 16, STYLE);
            svgDrawing.addRectangle(carportLength -300-shedLength-16-16,fasciaThickness+350-96/2-16+75+sum,100, 16, STYLE);
            //back
            svgDrawing.addRectangle(carportLength -300,fasciaThickness+350-96/2-16+sum,100, 16, STYLE);
            svgDrawing.addRectangle(carportLength -300+16,fasciaThickness+350-96/2-16+75+sum,100, 16, STYLE);

            sum += 150;
        }
        sum = 0;
        while (sum < shedLength-2*16)
        {
            //sides
            svgDrawing.addRectangle(carportLength -300-shedLength+sum,fasciaThickness+350-96/2-16,16, 100, STYLE);
            svgDrawing.addRectangle(carportLength -300-shedLength+75+sum,fasciaThickness+350-96/2-16-16,16, 100, STYLE);
            svgDrawing.addRectangle(carportLength -300-shedLength+sum,carportWidth-350+97,16, 100, STYLE);
            svgDrawing.addRectangle(carportLength -300-shedLength+75+sum,carportWidth-350+97+16,16, 100, STYLE);

            sum += 150;
        }

    }

    public String toString()
    {
        return svgDrawing.toString();
    }
}
