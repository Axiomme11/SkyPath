import javax.swing.*;

public class Main {

    public static String version = "v0.6";

    public static void main(String[] args) {

        System.out.println("");
        double VitesseKTS = 450;
        double DistanceNM = 275;
        double TempsH = 1.5;// 1.57h == 1h 34m

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

        System.out.println( ConvertDecimaltoSepare( 1.5 ) );


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
        DistanceNM = Math.round(DistanceNM * 100.0) / 100.0;

        return DistanceNM;
    }

    public static double CalcVitesse( double DistanceNM, double TempsH ) {

        double VitesseKTS = 0;

        // V = D/T --> Vitesse = Distance(nm) / Temps(h)
        VitesseKTS = DistanceNM / TempsH;
        VitesseKTS = Math.round(VitesseKTS * 100.0) / 100.0;

        return VitesseKTS;
    }


    public static String ConvertDecimaltoSepare( double heuresDecimales ) {
        int heures = (int) heuresDecimales;
        int minutes = (int) ((heuresDecimales - heures) * 60);
        return heures + "h " + minutes + "m";
    }

    public static double ConvertSepareToDecimal( double heures, double minutes ) {
        double heuresDecimales = ((heures * 60) + minutes) / 60.0;
        heuresDecimales = Math.round(heuresDecimales * 100.0) / 100.0;
        return heuresDecimales;
    }

    public static double ConvertFTtoM( double ft ) {
        ft = ft / 0.3048;
        return ft = Math.round(ft * 100.0) / 100.0;
    }

    public static double ConvertMtoFT( double m ) {
        m = m * 0.3048;
        return m = Math.round(m * 100.0) / 100.0;
    }

}