package de.florianmichael.betapackets.model.block.properties;

import de.florianmichael.betapackets.model.block.properties.enums.*;
import de.florianmichael.betapackets.model.position.Facing;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class StateProperty<T> {

    private static final Map<String, StateProperty<?>> properties = new HashMap<>();

    private static <T> void add(String name, Function<String, T> serializer, Function<T, String> deserializer) {
        properties.put(name, new StateProperty<>(name, serializer, deserializer));
    }

    static {
        add("snowy", Boolean::parseBoolean, Object::toString);
        add("attached", Boolean::parseBoolean, Object::toString);
        add("stage", Byte::parseByte, Object::toString);
        add("level", Byte::parseByte, Object::toString);
        add("axis", Axis::getByName, Axis::name);
        add("check_decay", Boolean::parseBoolean, Object::toString);
        add("decayable", Boolean::parseBoolean, Object::toString);
        add("wet", Boolean::parseBoolean, Object::toString);
        add("facing", Facing::getByName, Facing::name);
        add("triggered", Boolean::valueOf, Object::toString);
        add("occupied", Boolean::valueOf, Object::toString);
        add("powered", Boolean::valueOf, Object::toString);
        add("extended", Boolean::valueOf, Object::toString);
        add("short", Boolean::valueOf, Object::toString);
        add("seamless", Boolean::valueOf, Object::toString);
        add("explode", Boolean::valueOf, Object::toString);
        add("age", Byte::valueOf, Object::toString);
        add("power", Byte::valueOf, Object::toString);
        add("moisture", Byte::valueOf, Object::toString);
        add("rotation", Byte::valueOf, Object::toString);
        add("open", Boolean::valueOf, Object::toString);
        add("in_wall", Boolean::valueOf, Object::toString);
        add("conditional", Boolean::valueOf, Object::toString);
        add("waterlogged", Boolean::valueOf, Object::toString);
        add("north", Boolean::valueOf, Object::toString);
        add("south", Boolean::valueOf, Object::toString);
        add("east", Boolean::valueOf, Object::toString);
        add("west", Boolean::valueOf, Object::toString);
        add("down", Boolean::valueOf, Object::toString);
        add("up", Boolean::valueOf, Object::toString);
        add("honey_level", Byte::valueOf, Object::toString);
        add("inverted", Boolean::valueOf, Object::toString);
        add("distance", Byte::valueOf, Object::toString);
        add("persistent", Boolean::valueOf, Object::toString);
        add("enabled", Boolean::valueOf, Object::toString);
        add("disarmed", Boolean::valueOf, Object::toString);
        add("eye", Boolean::valueOf, Object::toString);
        add("has_bottle_0", Boolean::valueOf, Object::toString);
        add("has_bottle_1", Boolean::valueOf, Object::toString);
        add("has_bottle_2", Boolean::valueOf, Object::toString);
        add("locked", Boolean::valueOf, Object::toString);
        add("delay", Byte::valueOf, Object::toString);
        add("bites", Byte::valueOf, Object::toString);
        add("has_record", Boolean::valueOf, Object::toString);
        add("layers", Byte::parseByte, Object::toString);
        add("lit", Boolean::parseBoolean, Object::toString);
        add("drag", Boolean::parseBoolean, Object::toString);
        add("signal_fire", Boolean::parseBoolean, Object::toString);
        add("berries", Boolean::parseBoolean, Object::toString);
        add("cracked", Boolean::parseBoolean, Object::toString);
        add("slot_0_occupied", Boolean::parseBoolean, Object::toString);
        add("slot_1_occupied", Boolean::parseBoolean, Object::toString);
        add("slot_2_occupied", Boolean::parseBoolean, Object::toString);
        add("slot_3_occupied", Boolean::parseBoolean, Object::toString);
        add("slot_4_occupied", Boolean::parseBoolean, Object::toString);
        add("slot_5_occupied", Boolean::parseBoolean, Object::toString);
        add("hanging", Boolean::parseBoolean, Object::toString);
        add("bottom", Boolean::parseBoolean, Object::toString);
        add("bloom", Boolean::parseBoolean, Object::toString);
        add("can_summon", Boolean::parseBoolean, Object::toString);
        add("shrieking", Boolean::parseBoolean, Object::toString);
        add("has_book", Boolean::parseBoolean, Object::toString);
        add("unstable", Boolean::parseBoolean, Object::toString);
        add("flower_amount", Byte::parseByte, Object::toString);
        add("eggs", Byte::parseByte, Object::toString);
        add("pickles", Byte::parseByte, Object::toString);
        add("hatch", Byte::parseByte, Object::toString);
        add("dusted", Byte::parseByte, Object::toString);
        add("charges", Byte::parseByte, Object::toString);
        add("note", Byte::parseByte, Object::toString);
        add("layers", Byte::parseByte, Object::toString);
        add("candles", Byte::parseByte, Object::toString);
        add("part", Part::getByName, Enum::name);
        add("shape", Shape::getByName, Enum::name);
        add("half", Half::getByName, Enum::name);
        add("hinge", Hinge::getByName, Enum::name);
        add("mode", Mode::getByName, Enum::name);
        add("type", Type::getByName, Enum::name);
        add("face", Face::getByName, Enum::name);
        add("sculk_sensor_phase", SculkSensorPhase::getByName, Enum::name);
        add("attachment", Attachment::getByName, Enum::name);
        add("tilt", Tilt::getByName, Enum::name);
        add("leaves", Leaves::getByName, Enum::name);
        add("instrument", Instrument::getByName, Enum::name);
        add("orientation", Orientation::getByName, Enum::name);
        add("thickness", Thickness::getByName, Enum::name);
        add("vertical_direction", VerticalDirection::getByName, Enum::name);
    }

    public static StateProperty<?> getStateProperty(String name) {
        if (properties.get(name) == null)
            throw new IllegalArgumentException("Unknown StateProperty: " + name);
        return properties.get(name);
    }

    private String name;
    private Function<String, T> serializer;
    private Function<T, String> deserializer;

    public StateProperty(String name, Function<String, T> serializer, Function<T, String> deserializer) {
        this.name = name;
        this.serializer = serializer;
        this.deserializer = deserializer;
    }

    public Function<String, T> getSerializer() {
        return serializer;
    }

    public Function<T, String> getDeserializer() {
        return deserializer;
    }

    public String getName() {
        return name;
    }
}
