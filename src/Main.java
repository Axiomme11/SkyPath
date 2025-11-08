import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("");
        double VitesseKTS = 450;
        double DistanceNM = 275;
        double TempsH = 1.57;// 1.57h == 1h 34m

        SwingUtilities.invokeLater(MainSwing::new);

        /*  Fonction et formatage réponse du calcul du Temps
        TempsH = CalcTemps( DistanceNM,  VitesseKTS );
        System.out.println( "Calcul Temps avec V="+VitesseKTS +" "+ "D="+DistanceNM+" : " + TempsH + "h == " + ConvertHeuresMinutes( TempsH ) + " Temps estimé" );
         */

        /*
        DistanceNM = CalcDistance( VitesseKTS, TempsH );
        System.out.println( "Calcul Distance avec V="+VitesseKTS +"kts "+ "T="+TempsH+"h : " + DistanceNM + "nm estimé");
         */

        /*
        VitesseKTS = CalcVitesse( DistanceNM, TempsH );
        System.out.println( "Calcul Vitesse avec D="+DistanceNM +"nm "+ "T="+TempsH+"h : " + VitesseKTS + "kts estimé");
        */

    }

    public static double CalcTemps( double DistanceNM, double VitesseKTS ) {

        double TempsBrut = 0;

        // T = D/V --> Temps = Distance(nm) / Vitesse(kts)
        TempsBrut = DistanceNM / VitesseKTS;
        double HeureArrondi = Math.round(TempsBrut * 100.0) / 100.0;

        return HeureArrondi;
    }

    public static double CalcDistance( double VitesseKTS, double TempsH ) {

        double DistanceNM = 0;

        // D = V*T --> Distance = Vitesse(kts) * Temps(h)
        DistanceNM = VitesseKTS * TempsH;
        DistanceNM = Math.round(DistanceNM * 10.0) / 10.0;

        return DistanceNM;
    }

    public static double CalcVitesse( double DistanceNM, double TempsH ) {

        double VitesseKTS = 0;

        // V = D/T --> Vitesse = Distance(nm) / Temps(h)
        VitesseKTS = DistanceNM / TempsH;
        VitesseKTS = Math.round(DistanceNM * 10.0) / 10.0;

        return VitesseKTS;
    }


    public static String ConvertHeuresMinutes(double heuresDecimales) {
        int heures = (int) heuresDecimales;
        int minutes = (int) ((heuresDecimales - heures) * 60);
        return heures + "h " + minutes + "m";
    }


}