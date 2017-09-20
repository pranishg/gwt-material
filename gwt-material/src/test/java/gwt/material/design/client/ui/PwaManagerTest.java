/*
 * #%L
 * GwtMaterial
 * %%
 * Copyright (C) 2015 - 2017 GwtMaterialDesign
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package gwt.material.design.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import gwt.material.design.client.pwa.PwaManager;
import gwt.material.design.client.ui.base.MaterialWidgetTest;

import static gwt.material.design.jquery.client.api.JQuery.$;

public class PwaManagerTest extends MaterialWidgetTest {

    final String WEB_MANIFEST_URL = "manifest.json";
    final String SERVICE_WORKER_URL = "service-worker.js";
    final String THEME_COLOR = "#ffffff";

    public void init() {

        PwaManager.getInstance()
                .setServiceWorkerUrl(SERVICE_WORKER_URL)
                .setWebManifestUrl(WEB_MANIFEST_URL)
                .setThemeColor(THEME_COLOR)
                .load();

        checkManifest();
        checkMetaThemeColor();
        checkServiceWorker();
    }

    protected void checkManifest() {
        // Get the link manifest element and check whether it's null or not
        Element linkManifest = getLinkManifestElement();
        assertNotNull(linkManifest);
        // Check whether the link manifest has an href of expected result
        assertEquals(linkManifest.getAttribute("href"), WEB_MANIFEST_URL);
        // Unregister the PWA Manager
        PwaManager.getInstance().unLoad();
        assertNull(getLinkManifestElement());
        // Reload the PWA Manager
        PwaManager.getInstance().reload();
        assertNotNull($(getLinkManifestElement()));
    }

    protected void checkMetaThemeColor() {
        // Get the link manifest element and check whether it's null or not
        Element metaThemeColor = getMetaThemeColor();
        assertNotNull(metaThemeColor);
        // Check whether the link manifest has an href of expected result
        assertEquals(metaThemeColor.getAttribute("content"), THEME_COLOR);
        // Unregister the PWA Manager
        PwaManager.getInstance().unLoad();
        assertNull(getMetaThemeColor());
        // Reload the PWA Manager
        PwaManager.getInstance().reload();
        assertNotNull($(getMetaThemeColor()));
    }

    protected void checkServiceWorker() {
        PwaManager.getInstance().setServiceWorkerUrl(SERVICE_WORKER_URL);
    }

    protected Element getMetaThemeColor() {
        Element head = Document.get().getElementsByTagName("head").getItem(0);
        assertNotNull(head);
        return $(head).find("meta[name='theme-color']").asElement();
    }

    protected Element getLinkManifestElement() {
        Element head = Document.get().getElementsByTagName("head").getItem(0);
        assertNotNull(head);
        return $(head).find("link[rel='manifest']").asElement();
    }
}