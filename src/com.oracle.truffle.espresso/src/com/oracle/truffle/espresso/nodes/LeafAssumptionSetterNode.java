/*
 * Copyright (c) 2017, 2019, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package com.oracle.truffle.espresso.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.espresso.impl.Method;

public class LeafAssumptionSetterNode extends InlinedSetterNode {

    private final int curBCI;
    private final int opcode;

    protected LeafAssumptionSetterNode(Method inlinedMethod, int top, int opCode, int curBCI) {
        super(inlinedMethod, top, opCode, curBCI);
        this.curBCI = curBCI;
        this.opcode = opCode;
    }

    @Override
    public int execute(VirtualFrame frame) {
        BytecodesNode root = getBytecodesNode();
        if (inlinedMethod.leafAssumption()) {
            setFieldNode.setField(frame, root, top);
            return -slotCount + stackEffect;
        } else {
            return root.reQuickenInvoke(frame, top, curBCI, opcode, inlinedMethod);
        }
    }

}
