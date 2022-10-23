package it.polimi.ingsw.Server.Parser;

import it.polimi.ingsw.Server.Model.Root.DivinityCard;
import it.polimi.ingsw.Server.Model.TurnCreator.Effect;
import it.polimi.ingsw.Server.Model.TurnCreator.MultipleBuildCreator.*;
import it.polimi.ingsw.Server.Model.TurnCreator.MultipleMoveCreator.OnBorderOneMoreMoveCreator;
import it.polimi.ingsw.Server.Model.TurnCreator.MultipleMoveCreator.OneMoreMoveCreator;
import it.polimi.ingsw.Server.Model.TurnCreator.PowerfulBuildCreator.BuildDomeCreator;
import it.polimi.ingsw.Server.Model.TurnCreator.PowerfulBuildCreator.BuildUnderneathCreator;
import it.polimi.ingsw.Server.Model.TurnCreator.PowerfulMove.PushCreator;
import it.polimi.ingsw.Server.Model.TurnCreator.PowerfulMove.SwapCreator;
import it.polimi.ingsw.Server.Model.TurnCreator.SimpleTurnCreator;
import it.polimi.ingsw.Server.Model.TurnCreator.WinConditionCreator.BlockOpponentsCreator;
import it.polimi.ingsw.Server.Model.TurnCreator.WinConditionCreator.CompleteBuildWinCreator;
import it.polimi.ingsw.Server.Model.TurnCreator.WinConditionCreator.EasyWinCreator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ParserJson {


    private ArrayList<DivinityCard> ParserCardsInDeck = new ArrayList<>();

    public ArrayList<DivinityCard> getParserCardsInDeck() {
        return ParserCardsInDeck;
    }

    /**
     * this method parse a file  and creates a deck of DivinityCard cards usable in the game according to the number of players
     * it is invoked by the controller as soon as a game with x players is started
     */
    public ParserJson(int nPlayer) {
        String filePath = "divinities.json";
        try{
            String contents = new String((Files.readAllBytes(Paths.get(filePath))));
            JSONObject divinityList = new JSONObject(contents);

            JSONArray divinities = divinityList.getJSONArray("list_of_divinities");

            //LETTURA DI TUTTE LE DIVINITA' E AGGIUNTA DELLE DIVNITA' UTILIZZABILI IN PARTITE A 3 GIOCATORI
            if(nPlayer == 3) {
                for (int i = 0; i < divinities.length(); i++) {
                    if (divinities.getJSONObject(i).getBoolean("usable") == true) {
                        DivinityCard card = new DivinityCard();

                        card.setName(divinities.getJSONObject(i).getString("name"));
                        card.setInfo(divinities.getJSONObject(i).getString("info"));

                        //TODO chiedere se switch case è soluzione opportuna ed eventualmente sostituirlo

                        String effectJSON = divinities.getJSONObject(i).getString("effect");

                        switch (effectJSON){

                            case "swap" :
                                Effect swapCreator = new SwapCreator();
                                card.setEffect(swapCreator);
                                break;

                            case "one_more_move" :
                                Effect oneMoreMoveCreator = new OneMoreMoveCreator();
                                card.setEffect(oneMoreMoveCreator);
                                break;

                            case "block_opponents" :
                                Effect blockOpponentsCreator = new BlockOpponentsCreator();
                                card.setEffect(blockOpponentsCreator);
                                break;

                            case "build_dome" :
                                Effect buildDomeCreator = new BuildDomeCreator();
                                card.setEffect(buildDomeCreator);
                                break;

                            case "complete_build_win" :
                                Effect completeBuildWinCreator = new CompleteBuildWinCreator();
                                card.setEffect(completeBuildWinCreator);
                                break;

                            case "different_pos_one_more_build" :
                                Effect differentPosOneMoreBuildCreator = new DifferentPosOneMoreBuildCreator();
                                card.setEffect(differentPosOneMoreBuildCreator);
                                break;

                            case "same_pos_one_more_build" :
                                Effect samePosOneMoreBuildCreator = new SamePosOneMoreBuildCreator();
                                card.setEffect(samePosOneMoreBuildCreator);
                                break;

                            case "off_border_one_more_build" :
                                Effect offBorderOneMoreBuildCreator = new OffBorderOneMoreBuildCreator();
                                card.setEffect(offBorderOneMoreBuildCreator);
                                break;

                            case "push" :
                                Effect pushCreator = new PushCreator();
                                card.setEffect(pushCreator);
                                break;

                            case "easy_win" :
                                Effect easyWinCreator = new EasyWinCreator();
                                card.setEffect(easyWinCreator);
                                break;

                            case "on_ground_three_build" :
                                Effect onGroundThreeBuildCreator = new OnGroundThreeBuildCreator();
                                card.setEffect(onGroundThreeBuildCreator);
                                break;

                            case "on_level_double_build" :
                                Effect onLevelDoubleBuildCreator = new OnLevelDoubleBuildCreator();
                                card.setEffect(onLevelDoubleBuildCreator);
                                break;

                            case "on_border_one_more_move" :
                                Effect onBorderOneMoreMoveCreator = new OnBorderOneMoreMoveCreator();
                                card.setEffect(onBorderOneMoreMoveCreator);
                                break;

                            case "build_underneath" :
                                Effect buildUnderneathCreator = new BuildUnderneathCreator();
                                card.setEffect(buildUnderneathCreator);
                                break;

                            default :
                                Effect simpleTurnCreator = new SimpleTurnCreator();
                                card.setEffect(simpleTurnCreator);
                                break;
                        }

                        ParserCardsInDeck.add(card);
                    }
                }
            }

            //LETTURA DI TUTTE LE DIVINITA' E AGGIUNTA DI TUTTE LE DIVINITA' (CASO 2 GIOCATORI)
            else {                                                                //RIVEDERE CONDIZIONE ELSE! MAGARI SOSTITUIRLE UNA PIU' SPECIFICA
                for (int i = 0; i < divinities.length(); i++) {
                    DivinityCard card = new DivinityCard();

                    card.setName(divinities.getJSONObject(i).getString("name"));
                    card.setInfo(divinities.getJSONObject(i).getString("info"));

                    //TODO chiedere se switch case è soluzione opportuna ed eventualmente sostituirlo

                    String effectJSON = divinities.getJSONObject(i).getString("effect");

                    switch (effectJSON) {

                        case "swap":
                            Effect swapCreator = new SwapCreator();
                            card.setEffect(swapCreator);
                            break;

                        case "one_more_move":
                            Effect oneMoreMoveCreator = new OneMoreMoveCreator();
                            card.setEffect(oneMoreMoveCreator);
                            break;

                        case "block_opponents":
                            Effect blockOpponentsCreator = new BlockOpponentsCreator();
                            card.setEffect(blockOpponentsCreator);
                            break;

                        case "build_dome":
                            Effect buildDomeCreator = new BuildDomeCreator();
                            card.setEffect(buildDomeCreator);
                            break;

                        case "complete_build_win":
                            Effect completeBuildWinCreator = new CompleteBuildWinCreator();
                            card.setEffect(completeBuildWinCreator);
                            break;

                        case "different_pos_one_more_build":
                            Effect differentPosOneMoreBuildCreator = new DifferentPosOneMoreBuildCreator();
                            card.setEffect(differentPosOneMoreBuildCreator);
                            break;

                        case "same_pos_one_more_build":
                            Effect samePosOneMoreBuildCreator = new SamePosOneMoreBuildCreator();
                            card.setEffect(samePosOneMoreBuildCreator);
                            break;

                        case "off_border_one_more_build":
                            Effect offBorderOneMoreBuildCreator = new OffBorderOneMoreBuildCreator();
                            card.setEffect(offBorderOneMoreBuildCreator);
                            break;

                        case "push":
                            Effect pushCreator = new PushCreator();
                            card.setEffect(pushCreator);
                            break;

                        case "easy_win":
                            Effect easyWinCreator = new EasyWinCreator();
                            card.setEffect(easyWinCreator);
                            break;

                        case "on_ground_three_build":
                            Effect onGroundThreeBuildCreator = new OnGroundThreeBuildCreator();
                            card.setEffect(onGroundThreeBuildCreator);
                            break;

                        case "on_level_double_build":
                            Effect onLevelDoubleBuildCreator = new OnLevelDoubleBuildCreator();
                            card.setEffect(onLevelDoubleBuildCreator);
                            break;

                        case "on_border_one_more_move":
                            Effect onBorderOneMoreMoveCreator = new OnBorderOneMoreMoveCreator();
                            card.setEffect(onBorderOneMoreMoveCreator);
                            break;

                        case "build_underneath":
                            Effect buildUnderneathCreator = new BuildUnderneathCreator();
                            card.setEffect(buildUnderneathCreator);
                            break;

                        default:
                            Effect simpleTurnCreator = new SimpleTurnCreator();
                            card.setEffect(simpleTurnCreator);
                            break;
                    }

                    ParserCardsInDeck.add(card);
                }
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
