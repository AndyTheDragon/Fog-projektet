package app.Entities;

public class Carport
{
    public int[] calcOptimalWood(int totalLength, int length1, int length2)
    {
        double wastePercentage = 1.05;
        if (totalLength % length1 == 0 || totalLength % length2 == 0)
        {
            wastePercentage = 1.0;
        }
        double totalLengthInclWaste = totalLength * wastePercentage;
        int maxPieces = 10;     // Maks antal stykker at overveje

        // Variabler til at holde resultatet
        int bestX = 0, bestY = 0;
        double bestWaste = Double.MAX_VALUE;

        // Loop for at finde optimale kombinationer
        for (int x = 0; x <= maxPieces; x++)
        {
            for (int y = 0; y <= maxPieces; y++)
            {
                // Beregn samlet længde
                double calculatedLength = x * length2 + y * length1;

                // Tjek om denne kombination opfylder behovet
                if (calculatedLength >= totalLengthInclWaste)
                {
                    // Beregn spild
                    double waste = calculatedLength - totalLengthInclWaste;

                    // Opdater bedste resultat, hvis mindre spild findes
                    if (waste < bestWaste)
                    {
                        bestX = x;
                        bestY = y;
                        bestWaste = waste;
                    }
                }
            }

        }
        return new int[]{bestY, bestX};
    }
}
