package com.github.evseevda.swoyotesttask.core.command;

import lombok.*;

import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Command {

    private String command;
    private Map<String, String> args;

    @Override
    public String toString() {
        Set<Map.Entry<String, String>> entries = args.entrySet();
        String reduced = entries.stream()
                .map(entry -> "-" + entry.getKey() + "=" + entry.getValue())
                .reduce("", (accumulated, s) -> accumulated + " " + s);
        return command + reduced;
    }
}
