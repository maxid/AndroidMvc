package com.shipdream.lib.android.mvc;


import com.shipdream.lib.android.mvc.event.BaseEventC;
import com.shipdream.lib.android.mvc.event.bus.EventBus;
import com.shipdream.lib.android.mvc.event.bus.annotation.EventBusC;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Abstract manager to be extended to manage shared presenter logic and data.
 * @param <MODEL> The model the manager holds. On Android, models will be automatically
 *               serialized and deserialized by fragments when the manager is injected into a
 *               fragment as a class's field directly or indirectly(held by presenter's field).
 */
public abstract class Manager<MODEL> extends Bean<MODEL> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    @EventBusC
    private EventBus eventBus2C;

    /**
     * Bind model to this manager
     * @param sender Who wants to bind it
     * @param model The model to bind to this manager. CANNOT be NULL otherwise a runtime
     */
    public void bindModel(Object sender, MODEL model) {
        super.bindModel(model);
    }

    public void onCreated() {
        super.onCreated();
        eventBus2C.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus2C.unregister(this);
    }
    /**
     * Post an event to . Event2C will be posted on the same thread as the caller.
     *
     * @param event event to controllers
     */
    protected void postEvent2C(final BaseEventC event) {
        if (eventBus2C != null) {
            eventBus2C.post(event);
        } else {
            logger.warn("Trying to post event {} to EventBusC which is null", event.getClass().getName());
        }
    }
}
