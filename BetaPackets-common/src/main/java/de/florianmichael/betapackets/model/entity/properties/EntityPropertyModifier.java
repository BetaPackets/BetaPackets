/*
 * This file is part of BetaPackets - https://github.com/FlorianMichael/BetaPackets
 * Copyright (C) 2023 FlorianMichael/EnZaXD and contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.florianmichael.betapackets.model.entity.properties;

import java.util.Objects;
import java.util.UUID;

public class EntityPropertyModifier {

    public UUID uuid;
    public double amount;
    public Operation operation;

    public EntityPropertyModifier(UUID uuid, double amount, Operation operation) {
        this.uuid = uuid;
        this.amount = amount;
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "EntityPropertyModifier{" +
                "uuid=" + uuid +
                ", amount=" + amount +
                ", operation=" + operation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityPropertyModifier that = (EntityPropertyModifier) o;

        if (Double.compare(that.amount, amount) != 0) return false;
        if (!Objects.equals(uuid, that.uuid)) return false;
        return operation == that.operation;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = uuid != null ? uuid.hashCode() : 0;
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        return result;
    }
}
