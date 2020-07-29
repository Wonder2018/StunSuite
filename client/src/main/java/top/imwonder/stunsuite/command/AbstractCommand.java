/*
 * @Author: Wonder2019 
 * @Date: 2020-07-16 16:37:06 
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-07-16 17:21:40
 */
package top.imwonder.stunsuite.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;

@Getter
public abstract class AbstractCommand {

    protected Logger log = LoggerFactory.getLogger(getClass());

    protected String param;
    protected final String description;

    public AbstractCommand(String description) {
        this.description = description;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public abstract String excute();
}