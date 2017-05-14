package model;

import com.thoughtworks.xstream.XStream;


/**
 * Created by SurfinBirb on 21.04.2017.
 */
 class Unpacker {

    /**
     *
     * @param xmlString - String wiyh XML structure
     * @return Packet
     */
    public Packet unpack(String xmlString){
        XStream xstream = new XStream();
        xstream.alias("pack", Packet.class);
        xstream.alias("message", Message.class);
        xstream.alias("room", Room.class);
        xstream.alias("servicemessage", ServiceMessage.class);
        xstream.alias("auth", AuthData.class);
        return (Packet) xstream.fromXML(xmlString);
    }


}


