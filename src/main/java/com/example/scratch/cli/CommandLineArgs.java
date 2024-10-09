package com.example.scratch.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/*
 * @created 09.10.2024
 * @author Alexander Kabakov
 */
public class CommandLineArgs {

    public static final String CONFIG_PATH = "config";
    public static final String BETTING_AMOUNT = "betting-amount";

    private final String[] args;
    private final CommandLine commandLine;

    public CommandLineArgs(String[] args) throws ParseException {
        this.args = args;
        this.commandLine = buildCommandLine();
    }

    private CommandLine buildCommandLine() throws ParseException {
        Options options = new Options()
            .addOption(
                Option.builder(CONFIG_PATH)
                    .longOpt(CONFIG_PATH)
                    .hasArg()
                    .required()
                    .build())
            .addOption(
                Option.builder(BETTING_AMOUNT)
                    .longOpt(BETTING_AMOUNT)
                    .hasArg()
                    .required()
                    .build());
        return new DefaultParser().parse(options, args);
    }

    public String getConfigPath() {
        return commandLine.getOptionValue(CONFIG_PATH);
    }

    public int getBettingAmount() {
        return Integer.parseInt(commandLine.getOptionValue(BETTING_AMOUNT));
    }
}
