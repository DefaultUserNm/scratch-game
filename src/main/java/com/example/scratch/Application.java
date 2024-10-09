package com.example.scratch;

import com.example.scratch.cli.CommandLineArgs;
import com.example.scratch.configuration.ConfigLoader;
import com.example.scratch.configuration.properties.GameConfig;
import com.example.scratch.engine.GameRunner;
import com.example.scratch.model.GameResult;
import com.example.scratch.model.GameResultDto;
import com.example.scratch.util.MapperHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.ParseException;

import java.io.IOException;

import static java.lang.Math.round;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 * Game entrypoint
 */
public class Application {

    private static final ObjectMapper MAPPER = MapperHolder.getInstance();

    public static void main(String[] args) throws IOException, ParseException {
        setupLogging();
        CommandLineArgs parsedArgs = new CommandLineArgs(args);
        var result = playTheGame(parsedArgs.getConfigPath(), parsedArgs.getBettingAmount());
        printResult(result);
    }

    private static void setupLogging() {
        System.setProperty("slf4j.internal.verbosity", "WARN");
    }

    private static GameResult playTheGame(String configPath, Integer amount) throws IOException {
        GameConfig gameConfig = new ConfigLoader(configPath).load();
        return new GameRunner(gameConfig, amount).run();
    }

    private static void printResult(GameResult result) throws JsonProcessingException {
        var dto = new GameResultDto()
            .setMatrix(result.getMatrix().getMatrix())
            .setReward((int) round(result.getReward()))
            .setAppliedWinningCombinations(result.getAppliedWinningCombinations())
            .setAppliedBonusSymbol(result.getAppliedBonusSymbol());
        System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(dto));
    }
}
