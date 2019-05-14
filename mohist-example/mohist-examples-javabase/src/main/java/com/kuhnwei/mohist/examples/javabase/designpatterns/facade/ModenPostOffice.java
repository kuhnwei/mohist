package com.kuhnwei.mohist.examples.javabase.designpatterns.facade;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/21 14:07
 */
public class ModenPostOffice {
    private LetterProcess letterProcess = new LetterProcessImpl();
    private Police police = new Police();
    public void sendLetter(String context, String address) {
        letterProcess.writeContext(context);
        letterProcess.fillEnvelope(address);
        police.checkLetter(letterProcess);
        letterProcess.letterIntoEnvelope();
        letterProcess.sendLetter();
    }
}
