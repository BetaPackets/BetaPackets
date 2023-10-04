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

package org.betapackets.betapackets.model.entity.attribute;

import java.util.UUID;

public class AttributeModifier {

    private double amount;
    private Operation operation;
    private UUID uuid;

    public AttributeModifier(UUID uuid, double amount, Operation operation) {
        this.amount = amount;
        this.operation = operation;
        this.uuid = uuid;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public double getAmount() {
        return amount;
    }

    public Operation getOperation() {
        return operation;
    }

    public UUID getUuid() {
        return uuid;
    }

    public enum Operation {
        ADD,
        MULTIPLY_BASE,
        MULTIPLY_TOTAL;

        public int getId() {
            return ordinal();
        }

        public static Operation getById(int id) {
            for (Operation op : values())
                if (op.ordinal() == id) return op;
            return null;
        }
    }
}
