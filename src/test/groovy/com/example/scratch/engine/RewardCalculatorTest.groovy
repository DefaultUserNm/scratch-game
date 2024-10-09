package com.example.scratch.engine

import com.example.scratch.model.GameMatrix
import com.example.scratch.model.GameResult
import com.example.scratch.testData.TestGameMatrix1
import com.example.scratch.testData.TestGameMatrix2
import com.example.scratch.testData.TestGameMatrix3
import com.example.scratch.testData.TestGameMatrix4
import com.example.scratch.util.MapperHolder
import spock.lang.Specification

import static com.example.scratch.util.GameConfigUtil.getConfig
/*
 * @created 09.10.2024
 * @author Alexander Kabakov
 */
class RewardCalculatorTest extends Specification {

    private static final int BETTING_AMOUNT = 100
    private static final String CONFIG_PATH = "config.json"

    def 'verify algorithm by pre-calculated scenarios'() {
        when:
        def result = play(matrix)

        then:
        result.getReward() == expectedReward

        where:
        matrix                  | expectedReward
        new TestGameMatrix1()   | 3_000
        new TestGameMatrix2()   | 0
        new TestGameMatrix3()   | 125_000
        new TestGameMatrix4()   | 1_375
    }

    private GameResult play(GameMatrix matrix) {
        def config = getConfig(CONFIG_PATH)
        config.afterPropertiesSet()
        def result = new GameRunner(config, BETTING_AMOUNT).run(matrix)
        def stringResult = MapperHolder.getInstance().writeValueAsString(result)
        println("Test game result: $stringResult")

        return result
    }
}
