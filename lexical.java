import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;


/**
 * Created by Mohammed M Khan on 3/12/2017.
 * TO RUN Have this file and test.txt in src folder, then in cmd navigate to src folder and
 * javac lexical.java
 * then
 * java lexical
 * then there should be an output.txt file in the src created that contains the outputs.
 *
 * its a little unclean as its all in one class file, but it helped me to do it this way

 the objective is simple, it reads from the input and goes thru it for each type and lists it
  on the output file. I've attached a readme of the project requirements.

  pretty fun to create a dfa as it helped applying the concept i learned in my theory
  of computation course. Will try to do a parse tree next after my midterms.
 */

    public abstract class lexical {

        public enum State {
            // non-final states ordinal number

            Start, // 0 non final start
            Period, // 1
            E, // 2
            EPlusMinus, // 3
            bar,//4
            ampersand,//5

            // final states
            /** the next set of Id variables check all the
             * cases of ID followed by different keywords and everything
             * like iff, print, anything123 */

            Id, // 6
            id_i,//7
            id_in,//8
            id_f,//9
            id_fl,//10
            id_flo,//11
            id_floa,//12
            id_w,//13
            id_wh,//14
            id_whi,//15
            id_whil,//16
            id_e,//17
            id_el,//18
            id_els,//19
            id_d,//20
            id_b,//21
            id_bo,//22
            id_boo,//23
            id_bool,//24
            id_boole,//25
            id_boolea,//26
            id_fa,//27
            id_fal,//28
            id_fals,//29
            id_t,//30
            id_tr,//31
            id_tru,//32
            Keyword_if,//33 if
            Keyword_else,//34 else
            Keyword_while,//35 while
            Keyword_boolean,//36 boolean
            Keyword_do,//37  do
            Keyword_float,//38 float
            Keyword_int,//39 int
            Semicolon,//40 ;
            Assign,//41 =
            LBrace,//42 {
            RBrace,//43 }
            Eq,//44 eq ==
            inv,//45 !
            neq,//46 not equal
            Le,//47 less than and =
            Lt,//48 less than
            Ge,//49 greater than and =
            Gt,//50 greater than
            Incr,//51 test case for x++;
            Decr,//52  test case for x--;
            Comma,//53 ,
            Or,//54 ||
            And,//55 &&
            Keyword_true,//56 true
            Keyword_false,//57 false
            Int, // 58 Int
            Float, //59 Float
            FloatE, //60 FloatE
            Add, //61 +
            Sub, //62 -
            Mul, //63 *
            Div, //64 division
            LParen, //65 (
            RParen, //66 )
            Colon, //67 :

            UNDEF //undefined
        }

        // By enumerating the non-final states first and then the final states,
        // test for a final state can be done by testing if the state's ordinal
        // number
        // is greater than or equal to that of Id.

        public static String t; // holds an extracted token
        public static State state; // the current state of the FA
        private static int a; // the current input character
        private static char c; // used to convert the variable "a" to
        // the char type whenever necessary
        private static BufferedReader input; //input reader obj
        private static PrintWriter output;  //output writer obj

        private static int getNextChar()

                // returns the next character in the input

        {
            try {
                return input.read();
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        } // end of the getNextChar method

        private static int getChar()

        // returns the next character on the input not including a space.
        // returns a -1 if the input has reached the end.

        {
            int i = getNextChar();
            while (Character.isWhitespace((char) i))
                i = getNextChar();
            return i;
        } // end of getChar method

        private static int driver()

        // This is the driver of the FA.
        // If a valid token is found, assigns it to "t" and returns 1.
        // If an invalid token is found, assigns it to "t" and returns 0.
        // If end-of-stream is reached without finding any non-whitespace character,
        // returns -1.

        {
            State NEXTSTATE; // the next state of the FA

            t = "";
            state = State.Start;

            if (Character.isWhitespace((char) a))
                a = getChar(); // get the next non-whitespace character
            if (a == -1) // end-of-stream is reached
                return -1;

            while (a != -1) // while "a" is not end-of-stream
            {
                c = (char) a;
                NEXTSTATE = nextState(state, c);
                if (NEXTSTATE == State.UNDEF) // The FA will halt.
                {
                    if (isFinal(state)) //pass in state into my isFinal method
                        return 1; // valid token extracted
                    else // "c" is an unexpected character
                    {
                        t = t + c;
                        a = getNextChar();
                        return 0; // invalid token found
                    }
                } else // The FA will go on.
                {
                    state = NEXTSTATE;
                    t = t + c;
                    a = getNextChar();
                }
            }

            // end-of-stream is reached while a token is being extracted

            if (isFinal(state)) //pass in state to isfinal method
                return 1; // valid token extracted
            else
                return 0; // invalid token found
        } // end driver

        private static State nextState(State s, char c)

        // Returns the next state of the FA given the current state an input char;
        // if the next state is undefined, UNDEF is returned.
        // If the next state is final
        //then the state name is returned to an output file named output.txt

        {
            switch (state) {
                case Start:
                    if(c == 'i')
                        return State.id_i;
                    else if(c == 't')
                        return State.id_t;
                    else if(c == 'b')
                        return State.id_b;
                    else if(c == 'd')
                        return State.id_d;
                    else if(c == 'e')
                        return State.id_e;
                    else if(c == 'f')
                        return State.id_f;
                    else if(c == 'w')
                        return State.id_w;
                    else if (Character.isLetter(c))
                        return State.Id;
                    else if (Character.isDigit(c))
                        return State.Int;
                    else if (c == '+')
                        return State.Add;
                    else if (c == '-')
                        return State.Sub;
                    else if (c == '*')
                        return State.Mul;
                    else if (c == '/')
                        return State.Div;
                    else if (c == '(')
                        return State.LParen;
                    else if (c == ')')
                        return State.RParen;
                    else if(c == '.')
                        return State.Period;
                    else if(c == '{')
                        return State.LBrace;
                    else if(c == '}')
                        return State.RBrace;
                    else if(c == ',')
                        return State.Comma;
                    else if(c == ';')
                        return State.Semicolon;
                    else if(c == '=')
                        return State.Assign;
                    else if(c == '>')
                        return State.Gt;
                    else if(c == '<')
                        return State.Lt;
                    else if(c == '!')
                        return State.inv;
                    else if(c == '|')
                        return State.bar;
                    else if(c == '&')
                        return State.ampersand;
                    else if(c == ':')
                        return State.Colon;
                    else
                        return State.UNDEF;

                    //----------------------

                case id_t:
                    if(c == 'r')
                        return State.id_tr;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case id_tr:
                    if(c == 'u')
                        return State.id_tru;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case id_tru:
                    if(c == 'e')
                        return State.Keyword_true;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                    //----------------------
                    //checks for keyword_boolean
                case id_b:
                    if(c == 'o')
                        return State.id_bo;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case id_bo:
                    if(c == 'o')
                        return State.id_boo;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case id_boo:
                    if(c == 'l')
                        return State.id_bool;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case id_bool:
                    if(c == 'e')
                        return State.id_boole;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case id_boole:
                    if(c == 'a')
                        return State.id_boolea;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case id_boolea:
                    if(c == 'n')
                        return State.Keyword_boolean;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                    //----------------------
                    //check for the keyword_do
                case id_d:
                    if(c == 'o')
                        return State.Keyword_do;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                    //----------------------
                    //checks for the keyword_else
                case id_e:
                    if(c == 'l')
                        return State.id_el;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case id_el:
                    if(c == 's')
                        return State.id_els;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case id_els:
                    if(c == 'e')
                        return State.Keyword_else;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                    //----------------------
                    //check for the keyword_while
                case id_w:
                    if(c == 'h')
                        return State.id_wh;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case id_wh:
                    if(c == 'i')
                        return State.id_whi;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case id_whi:
                    if(c == 'l')
                        return State.id_whil;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case id_whil:
                    if(c == 'e')
                        return State.Keyword_while;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                    //---------------------------------------
                    //checks for the keyword_if
                case id_i:
                    if(c == 'f')
                        return State.Keyword_if;
                    else if(c == 'n')
                        return State.id_in;
                    else if(Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                    //-----------------------------------
                    //checks for the keyword_float
                case id_f:
                    if(c == 'l')
                        return State.id_fl;
                    else if(c == 'a')
                        return State.id_fa;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case id_fl:
                    if(c == 'o')
                        return State.id_flo;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case id_flo:
                    if(c == 'a')
                        return State.id_floa;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case id_floa:
                    if(c == 't')
                        return State.Keyword_float;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                    //--------------------------------
                    //checks for the keyword_false
                case id_fa:
                    if(c == 'l')
                        return State.id_fal;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case id_fal:
                    if (c == 's')
                        return State.id_fals;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case id_fals:
                    if(c == 'e')
                        return State.Keyword_false;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                    //----------------------------------------
                case Keyword_if:
                    if(Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                    //--------------------------------------------
                    //check the final states of every keyword.
                case id_in:
                    if(c == 't')
                        return State.Keyword_int;
                    else if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case Keyword_int:
                    if(Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case Keyword_float:
                    if(Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case Keyword_while:
                    if(Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case Keyword_else:
                    if(Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case Keyword_do:
                    if(Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case Keyword_boolean:
                    if(Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case Keyword_false:
                    if(Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case Keyword_true:
                    if(Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                    //---------------------------------------
                    //check for the  non keyword states.
                case Id:
                    if (Character.isLetterOrDigit(c))
                        return State.Id;
                    else
                        return State.UNDEF;
                case Int:
                    if (Character.isDigit(c))
                        return State.Int;
                    else if (c == '.')
                        return State.Float;
                    else
                        return State.UNDEF;
                case Period:
                    if (Character.isDigit(c))
                        return State.Float;
                    else
                        return State.UNDEF;
                case Float:
                    if (Character.isDigit(c))
                        return State.Float;
                    else if (c == 'e' || c == 'E')
                        return State.E;
                    else
                        return State.UNDEF;
                case E:
                    if (Character.isDigit(c))
                        return State.FloatE;
                    else if (c == '+' || c == '-')
                        return State.EPlusMinus;
                    else
                        return State.UNDEF;
                case EPlusMinus:
                    if (Character.isDigit(c))
                        return State.FloatE;
                    else
                        return State.UNDEF;
                case FloatE:
                    if (Character.isDigit(c))
                        return State.FloatE;
                    else
                        return State.UNDEF;
                case Assign:
                    if(c == '=')
                        return State.Eq;
                    else
                        return State.UNDEF;
                case inv:
                    if(c == '=')
                        return State.neq;
                    else
                        return State.UNDEF;
                case Gt:
                    if(c == '=')
                        return State.Ge;
                    else
                        return State.UNDEF;
                case Lt:
                    if(c == '=')
                        return State.Le;
                    else
                        return State.UNDEF;
                case Add:
                    if(c == '+')
                        return State.Incr;
                    else
                        return State.UNDEF;
                case Sub:
                    if(c == '-')
                        return State.Decr;
                    else
                        return State.UNDEF;
                case bar:
                    if(c == '|')
                        return State.Or;
                    else
                        return State.UNDEF;
                case ampersand:
                    if (c == '&')
                        return State.And;
                    else
                        return State.UNDEF;
                case Colon:
                    if (c == ':')
                        return State.Colon;
                    else
                        return State.UNDEF;
                default:
                    return State.UNDEF;
            }
        } // end nextState

        private static boolean isFinal(State passState) {
            /** This method is to check if the current state of the machine is final
             * we pass in the state we want to check and make sure it is > the lowest final state in enum*/

            return (passState.compareTo(State.Id) >= 0);
        }



        public static void displayln(String toDisplay) {
            output.println(toDisplay);
        }



        public static void startLexical(String inputFile, String outputFile)

        // Sets the input file as test" and "outputFile as output",
        // respectively.


        {
            try {
                input = new BufferedReader(new FileReader(inputFile));
                output = new PrintWriter(new FileOutputStream(outputFile));
                a = input.read();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } // end setIO

        public static void closeIO() {
            try {
                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } // end closeIO


        public static void main(String[] args) {
            {
                int i;
                            //first param is input, second param is output
                startLexical("test.txt", "output.txt");

                while (a != -1) // while "a" is not end-of-stream
                {
                    i = driver(); // extract the next token
                    if (i == 1){
                        if(state.compareTo(state.id_i)>=0 && state.compareTo(state.id_tru)<=0){
                            displayln(t+"	: "+ state.Id);
                        }else{
                            displayln(t + "   : " + state.toString());
                        }
                    }
                    else if (i == 0)
                        displayln(t + "  : Lexical Error, invalid Token");
                }

                closeIO();

            }
        }


    }
