/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.bc.client.navbar;



import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.errai.ui.client.local.spi.TranslationService;
import org.kie.bc.client.resources.i18n.Constants;
import org.uberfire.client.workbench.widgets.menu.megamenu.brand.MegaMenuBrand;

@ApplicationScoped
public class MegaMenuConfig implements MegaMenuBrand {

    @Inject
    private TranslationService translationService;

    @Override
    public String brandImageUrl() {
        return "images/business-central.png";
    }

    @Override
    public String brandImageLabel() {
        return translationService.format(Constants.ProductName);
    }

    @Override
    public String menuAccessorLabel() {
        return null;
    }
}
