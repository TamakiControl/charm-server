package com.tamakicontrol.utils;

import com.inductiveautomation.ignition.common.sqltags.model.types.TagQuality;
import com.inductiveautomation.ignition.common.sqltags.model.types.TagType;
import com.inductiveautomation.ignition.gateway.sqltags.simple.SimpleTagProvider;
import com.tamakicontrol.provider.CharmTagProvider;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * POJOToTagMapper (Plain old Java Object)
 * Maps a provided java object to the simple tag provider
 * */
public class POJOToTagMapper<T> {

    private static final Logger logger = LoggerFactory.getLogger(POJOToTagMapper.class);

    private final Class<T> type;
    private final T entity;
    private final String rootPath;
    private final SimpleTagProvider provider = CharmTagProvider.getInstance().getTagProvider();

    @SuppressWarnings("unchecked")
    public POJOToTagMapper(String rootPath, T entity) {
        this.entity = entity;
        this.rootPath = rootPath;
        this.type = (Class<T>) entity.getClass();
    }

    /**
     * Builds All Tags for Associated Object in Managed Tag Provider
     */
    public void configureTags() {

        for (Field field : type.getDeclaredFields()) {
            PublishTag tag = field.getAnnotation(PublishTag.class);
            if (tag != null) {
                logger.trace(String.format("Configuring Tag %s", tag.name()));
                String tagPath = rootPath + tag.name();
                provider.configureTag(tagPath, tag.dataType(), TagType.Custom);
            }
        }
    }

    /**
     * Remove All Tags from Managed Tag Provider
     */
    public void removeTags() {
        for (Field field : type.getDeclaredFields()) {
            PublishTag tag = field.getAnnotation(PublishTag.class);
            if (tag != null) {
                String tagPath = rootPath + tag.name();
                logger.trace(String.format("Removing Tag %s", tagPath));
                provider.removeTag(tagPath);
            }
        }
    }

    /**
     * Update All Tag Values
     * */
    public void updateTags(){
        for (Field field : type.getDeclaredFields()) {
            PublishTag tag = field.getAnnotation(PublishTag.class);
            if (tag != null) {
                String tagPath = rootPath + tag.name();
                logger.trace(String.format("Updating Tag %s", tagPath));
                try {
                    Object value = BeanUtils.getProperty(entity, field.getName());
                    provider.updateValue(tagPath, value, TagQuality.GOOD);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    logger.error(String.format("Exception Thrown While Updating Field %s for Class %s",
                            field.getName(), type.toString()), e);
                    provider.updateValue(tagPath, null, TagQuality.BAD);
                }
            }
        }
    }

}
