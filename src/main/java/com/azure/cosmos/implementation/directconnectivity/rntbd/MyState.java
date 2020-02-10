package com.azure.cosmos.implementation.directconnectivity.rntbd;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.stream.Collector;

@State(Scope.Thread)
public class MyState {
    final ImmutableSet<RntbdConstants.RntbdRequestHeader> set = RntbdConstants.RntbdRequestHeader.set;
    final Collector<RntbdConstants.RntbdRequestHeader, ?, ImmutableMap<RntbdConstants.RntbdRequestHeader, RntbdToken>> collector = Maps.toImmutableEnumMap(h -> h, RntbdToken::create);
}
