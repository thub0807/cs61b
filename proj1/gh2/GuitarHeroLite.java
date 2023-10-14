package gh2;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A client that uses the synthesizer package to replicate a plucked guitar string sound
 */
public class GuitarHeroLite {
    public static final double CONCERT_A = 440.0;
    //public static final double CONCERT_C = CONCERT_A * Math.pow(2, 3.0 / 12.0);
    public static  final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {

        GuitarString[] strings = new GuitarString[keyboard.length()];
        for (int i=0;i<keyboard.length();i++){
            double insert = CONCERT_A*Math.pow(2,((i-24.0)/12.0));
            strings[i] = new GuitarString(insert);
        }

        while (true) {
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                for (int i=0;i<keyboard.length();i++){
                    if (i == keyboard.indexOf(key)){
                        strings[i].pluck();
                        break;
                    }
                }
            }


            /* compute the superposition of samples */
            double sample =0.0;
            for (int i=0;i<keyboard.length();i++){
                sample += strings[i].sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (int i=0;i<keyboard.length();i++){
                strings[i].tic();
            }

        }
    }
}

