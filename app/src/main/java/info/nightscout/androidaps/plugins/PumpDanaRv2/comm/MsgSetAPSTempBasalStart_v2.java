package info.nightscout.androidaps.plugins.PumpDanaRv2.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.nightscout.androidaps.Config;
import info.nightscout.androidaps.plugins.PumpDanaR.comm.MessageBase;

public class MsgSetAPSTempBasalStart_v2 extends MessageBase {
    private static Logger log = LoggerFactory.getLogger(MsgSetAPSTempBasalStart_v2.class);

    public MsgSetAPSTempBasalStart_v2() {
        SetCommand(0xE002);
    }

    public MsgSetAPSTempBasalStart_v2(int percent) {
        this();

        //HARDCODED LIMITS
        if (percent < 0) percent = 0;
        if (percent > 500) percent = 500;

        AddParamInt(percent);
        if (percent < 100) {
            AddParamByte((byte) 0xA0); // 160
            if (Config.logDanaMessageDetail)
                log.debug("APS Temp basal start percent: " + percent + " duration 30 min");
        } else {
            AddParamByte((byte) 0x96); // 150
            if (Config.logDanaMessageDetail)
                log.debug("APS Temp basal start percent: " + percent + " duration 15 min");
        }

    }

    public void handleMessage(byte[] bytes) {
        int result = intFromBuff(bytes, 0, 1);
        if (result != 1) {
            failed = true;
            log.debug("Set APS temp basal start result: " + result + " FAILED!!!");
        } else {
            if (Config.logDanaMessageDetail)
                log.debug("Set APS temp basal start result: " + result);
        }
    }


}
