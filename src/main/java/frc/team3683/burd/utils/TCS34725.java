package frc.team3683.burd.utils;

import edu.wpi.first.wpilibj.I2C;

import java.util.ArrayList;
import java.util.List;

import static edu.wpi.first.wpilibj.Timer.delay;

/**************************************************************************/
public class TCS34725 extends AdafruitI2C {
    private boolean _tcs34725Initialised;
    private tcs34725IntegrationTime_t _tcs34725IntegrationTime;
    private tcs34725Gain_t _tcs34725Gain;
    public static final byte TCS34725_ADDRESS_A = (byte) req_t_tcs.TCS34725_ADDRESS.getVal();

    public enum req_t_tcs {

        TCS34725_ADDRESS    (0x29),
        TCS34725_COMMAND_BIT    (0x80),

        TCS34725_ENABLE(0x00),
        TCS34725_ENABLE_AIEN(0x10),  /* RGBC Interrupt Enable */
        TCS34725_ENABLE_WEN(0x08),    /* Wait enable - Writing 1 activates the wait timer */
        TCS34725_ENABLE_AEN(0x02),   /* RGBC Enable - Writing 1 actives the ADC, 0 disables it */
        TCS34725_ENABLE_PON(0x01),   /* Power on - Writing 1 activates the internal oscillator, 0 disables it */
        TCS34725_ATIME(0x01),    /* Integration time */
        TCS34725_WTIME(0x03),   /* Wait time (if req_t_tcs.TCS34725_ENABLE_WEN.getVal() is asserted) */
        TCS34725_WTIME_2_4MS(0xFF),    /* WLONG0 = 2.4ms   WLONG1 = 0.029s */
        TCS34725_WTIME_204MS(0xAB),   /* WLONG0 = 204ms   WLONG1 = 2.45s  */
        TCS34725_WTIME_614MS(0x00),   /* WLONG0 = 614ms   WLONG1 = 7.4s   */
        TCS34725_AILTL(0x04),    /* Clear channel lower interrupt threshold */
        TCS34725_AILTH(0x05),
        TCS34725_AIHTL(0x06),   /* Clear channel upper interrupt threshold */
        TCS34725_AIHTH(0x07),
        TCS34725_PERS(0x0C),  /* Persistence register - basic SW filtering mechanism for interrupts */
        TCS34725_PERS_NONE(0b0000),  /* Every RGBC cycle generates an interrupt                                */
        TCS34725_PERS_1_CYCLE(0b0001),  /* 1 clean channel value outside threshold range generates an interrupt   */
        TCS34725_PERS_2_CYCLE(0b0010), /* 2 clean channel values outside threshold range generates an interrupt  */
        TCS34725_PERS_3_CYCLE(0b0011), /* 3 clean channel values outside threshold range generates an interrupt  */
        TCS34725_PERS_5_CYCLE(0b0100), /* 5 clean channel values outside threshold range generates an interrupt  */
        TCS34725_PERS_10_CYCLE(0b0101),  /* 10 clean channel values outside threshold range generates an interrupt */
        TCS34725_PERS_15_CYCLE(0b0110), /* 15 clean channel values outside threshold range generates an interrupt */
        TCS34725_PERS_20_CYCLE(0b0111), /* 20 clean channel values outside threshold range generates an interrupt */
        TCS34725_PERS_25_CYCLE(0b1000), /* 25 clean channel values outside threshold range generates an interrupt */
        TCS34725_PERS_30_CYCLE(0b1001), /* 30 clean channel values outside threshold range generates an interrupt */
        TCS34725_PERS_35_CYCLE(0b1010), /* 35 clean channel values outside threshold range generates an interrupt */
        TCS34725_PERS_40_CYCLE(0b1011), /* 40 clean channel values outside threshold range generates an interrupt */
        TCS34725_PERS_45_CYCLE(0b1100), /* 45 clean channel values outside threshold range generates an interrupt */
        TCS34725_PERS_50_CYCLE(0b1101), /* 50 clean channel values outside threshold range generates an interrupt */
        TCS34725_PERS_55_CYCLE(0b1110), /* 55 clean channel values outside threshold range generates an interrupt */
        TCS34725_PERS_60_CYCLE(0b1111), /* 60 clean channel values outside threshold range generates an interrupt */
        TCS34725_CONFIG(0x0D),
        TCS34725_CONFIG_WLONG(0x02),    /* Choose between short and long (12x) wait times via TCS34725_WTIME */
        TCS34725_CONTROL(0x0F),    /* Set the gain level for the sensor */
        TCS34725_ID(0x12),    /* 0x44 = TCS34721/TCS34725, 0x4D = TCS34723/TCS34727 */
        TCS34725_STATUS(0x13),
        TCS34725_STATUS_AINT(0x10),    /* RGBC Clean channel interrupt */
        TCS34725_STATUS_AVALID(0x01),    /* Indicates that the RGBC channels have completed an integration cycle */
        TCS34725_CDATAL(0x14),    /* Clear channel data */
        TCS34725_CDATAH(0x15),
        TCS34725_RDATAL(0x16),   /* Red channel data */
        TCS34725_RDATAH(0x17),
        TCS34725_GDATAL(0x18),    /* Green channel data */
        TCS34725_GDATAH(0x19),
        TCS34725_BDATAL(0x1A),    /* Blue channel data */
        TCS34725_BDATAH(0x1B);

        private final int val;

        req_t_tcs(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    public enum tcs34725IntegrationTime_t
    {
        TCS34725_INTEGRATIONTIME_2_4MS  (0xFF),   /**<  2.4ms - 1 cycle    - Max Count: 1024  */
        TCS34725_INTEGRATIONTIME_24MS  (0xF6),   /**<  24ms  - 10 cycles  - Max Count: 10240 */
        TCS34725_INTEGRATIONTIME_50MS  (0xEB),   /**<  50ms  - 20 cycles  - Max Count: 20480 */
        TCS34725_INTEGRATIONTIME_101MS  (0xD5),   /**<  101ms - 42 cycles  - Max Count: 43008 */
        TCS34725_INTEGRATIONTIME_154MS  (0xC0),   /**<  154ms - 64 cycles  - Max Count: 65535 */
        TCS34725_INTEGRATIONTIME_700MS  (0x00);    /**<  700ms - 256 cycles - Max Count: 65535 */

        private final int val;

        tcs34725IntegrationTime_t(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    public enum tcs34725Gain_t
    {
        TCS34725_GAIN_1X  (0x00),   /**<  No gain  */
        TCS34725_GAIN_4X  (0x01),   /**<  4x gain  */
        TCS34725_GAIN_16X  (0x02),   /**<  16x gain */
        TCS34725_GAIN_60X  (0x03);    /**<  60x gain */

    private final int val;

    tcs34725Gain_t(int val) {
        this.val = val;
    }
    public int getVal() {
            return val;
        }
    }

/*========================================================================*/
/*                          PRIVATE FUNCTIONS                             */
/*========================================================================*/

/**
 * Enables the device
 */
   private void enable() {
        write8(req_t_tcs.TCS34725_ENABLE.getVal(), req_t_tcs.TCS34725_ENABLE_PON.getVal());
        delay(3);
        write8(req_t_tcs.TCS34725_ENABLE.getVal(), req_t_tcs.TCS34725_ENABLE_PON.getVal() | req_t_tcs.TCS34725_ENABLE_AEN.getVal());
    }

    /**
     * Disables the device (putting it in lower power sleep mode)
     */
   private void disable() {
  /* Turn the device off to save power */
        byte reg = 0;
        reg = read8(req_t_tcs.TCS34725_ENABLE.getVal());
        write8(req_t_tcs.TCS34725_ENABLE.getVal(), reg & ~(req_t_tcs.TCS34725_ENABLE_PON.getVal() | req_t_tcs.TCS34725_ENABLE_AEN.getVal()));
    }

/*========================================================================*/
/*                            CONSTRUCTORS                                */
/*========================================================================*/

/**************************************************************************/
/*!
    Constructor
*/
/**************************************************************************/

    /**
     *
     * @param it, the integraction time, currently unused
     * @param gain, the increase in exposure and light increase
     * @param port, which I2C port to use on the roborio
     * @param address, the I2C address of the TCS34725 sensor
     */
    public TCS34725(tcs34725IntegrationTime_t it, tcs34725Gain_t gain, I2C.Port port, byte address) {
        super(port, address, Device.TCS34725);
        _tcs34725Initialised = false;
        _tcs34725IntegrationTime = it;
        _tcs34725Gain = gain;
        begin();
    }

/*========================================================================*/
/*                           PUBLIC FUNCTIONS                             */
/*========================================================================*/

    /**
     * Initializes I2C and configures the sensor (call this function before
     * doing anything else)
     * @return true if successfully initialized, false if it failed
     */
    public boolean begin() {

        byte x = read8((byte) req_t_tcs.TCS34725_ID.getVal());
        if ((x != (byte)0x44) && (x != (byte)0x10)) {
//            System.out.println("Colour Sensor is not initialized. Something went wrong");
//            System.out.println(String.format(
//                    "Return bit, %s, should be %s or %s",
//                    Integer.toBinaryString(x),
//                    Integer.toBinaryString(0x44),
//                    Integer.toBinaryString(0x10)));
            return false;
        }
        _tcs34725Initialised = true;
        System.out.println("Colour Sensor Initialized");

  /* Set default integration time and gain */
        setIntegrationTime(_tcs34725IntegrationTime);
        setGain(_tcs34725Gain);

  /* Note: by default, the device is in power down mode on bootup */
        enable();

        return true;
    }

/**
*    Sets the integration time for the TC34725
*/
    public void setIntegrationTime(tcs34725IntegrationTime_t it) {
        if (!_tcs34725Initialised) begin();

  /* Update the timing register */
        write8(req_t_tcs.TCS34725_ATIME.getVal(), it.getVal());

  /* Update value placeholders */
        _tcs34725IntegrationTime = it;
    }

/**
*    Adjusts the gain on the TCS34725 (adjusts the sensitivity to light)
*/
    public void setGain(tcs34725Gain_t gain) {
        if (!_tcs34725Initialised) begin();

  /* Update the timing register */
        write8(req_t_tcs.TCS34725_CONTROL.getVal(), gain.getVal());

  /* Update value placeholders */
        _tcs34725Gain = gain;
    }

    /**
     * Return positive values for red, green, blue, and clear channel values
     *  @return A list, as entry 0, 1, 2, 3 being clear, red, green, and blue respectfully, all positive
     */
    public List<Integer> getPositiveData(){
        List<Short> rawData = getRawData();
        List<Integer> returnList = new ArrayList<>();
        for(short raw : rawData){
            returnList.add(Short.toUnsignedInt(raw));
        }
        return returnList;
    }

/**
 *   Reads the raw red, green, blue and clear channel values
 *   @return A list, as entry 0, 1, 2, 3 being clear, red, green, and blue respectfully
 *
 */
    public List<Short> getRawData() {
        if (!_tcs34725Initialised){
            begin();
        }

        List<Short> returnList = new ArrayList<>();

        returnList.add(read16(req_t_tcs.TCS34725_CDATAL.getVal()));
        returnList.add(read16(req_t_tcs.TCS34725_RDATAL.getVal()));
        returnList.add(read16(req_t_tcs.TCS34725_GDATAL.getVal()));
        returnList.add(read16(req_t_tcs.TCS34725_BDATAL.getVal()));

  /* Set a delay for the integration time */
//        switch (_tcs34725IntegrationTime) {
//            case TCS34725_INTEGRATIONTIME_2_4MS:
//                delay(3);
//                break;
//            case TCS34725_INTEGRATIONTIME_24MS:
//                delay(24);
//                break;
//            case TCS34725_INTEGRATIONTIME_50MS:
//                delay(50);
//                break;
//            case TCS34725_INTEGRATIONTIME_101MS:
//                delay(101);
//                break;
//            case TCS34725_INTEGRATIONTIME_154MS:
//                delay(154);
//                break;
//            case TCS34725_INTEGRATIONTIME_700MS:
//                delay(700);
//                break;
//        }

        return returnList;
    }

/**
 *
 *    Converts the raw R/G/B values to color temperature in degrees Kelvin
 *    @param r, red 16bit value
 *    @param g, greed 16bit value
 *    @param b, blue 16bit value
 *
 */

    public short calculateColorTemperature(short r, short g, short b) {
        float X, Y, Z;      /* RGB to XYZ correlation      */
        float xc, yc;       /* Chromaticity co-ordinates   */
        float n;            /* McCamy's formula            */
        float cct;

  /* 1. Map RGB values to their XYZ counterparts.    */
  /* Based on 6500K fluorescent, 3000K fluorescent   */
  /* and 60W incandescent values for a wide range.   */
  /* Note: Y = Illuminance or lux                    */
        X = (-0.14282F * r) + (1.54924F * g) + (-0.95641F * b);
        Y = (-0.32466F * r) + (1.57837F * g) + (-0.73191F * b);
        Z = (-0.68202F * r) + (0.77073F * g) + (0.56332F * b);

  /* 2. Calculate the chromaticity co-ordinates      */
        xc = (X) / (X + Y + Z);
        yc = (Y) / (X + Y + Z);

  /* 3. Use McCamy's formula to determine the CCT    */
        n = (xc - 0.3320F) / (0.1858F - yc);

  /* Calculate the final CCT */
        cct = (float) ((449.0F * Math.pow(n, 3)) + (3525.0F * Math.pow(n, 2)) + (6823.3F * n) + 5520.33F);

  /* Return the results in degrees Kelvin */
        return (short) cct;
    }

/**
 *
 *  Converts the raw R/G/B values to lux
 *    @param r, red 16bit value
 *    @param g, greed 16bit value
 *    @param b, blue 16bit value
 *
 */
    public short calculateLux(short r, short g, short b) {
        float illuminance;

  /* This only uses RGB ... how can we integrate clear or calculate lux */
  /* based exclusively on clear since this might be more reliable?      */
        illuminance = (-0.32466F * r) + (1.57837F * g) + (-0.73191F * b);

        return (short) illuminance;
    }


    public void setInterrupt(boolean i) {
        byte r = read8(req_t_tcs.TCS34725_ENABLE.getVal());
        if (i) {
            r |= req_t_tcs.TCS34725_ENABLE_AIEN.getVal();
        } else {
            r &= ~req_t_tcs.TCS34725_ENABLE_AIEN.getVal();
        }
        write8(req_t_tcs.TCS34725_ENABLE.getVal(), r);
    }

    public void setIntLimits(short low, short high) {
        write8((byte) 0x04, low & 0xFF);
        write8((byte) 0x05, low >> 8);
        write8((byte) 0x06, high & 0xFF);
        write8((byte) 0x07, high >> 8);
    }

}