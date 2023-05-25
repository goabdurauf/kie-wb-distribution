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

package org.kie.bc.client.home;



import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Inject;
import org.jboss.errai.common.client.api.Caller;

import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.ui.client.local.spi.TranslationService;
import org.kie.bc.client.resources.i18n.Constants;
import org.kie.workbench.common.screens.home.client.widgets.shortcut.utils.ShortcutHelper;
import org.kie.workbench.common.screens.home.model.HomeModel;
import org.kie.workbench.common.screens.home.model.HomeShortcut;
import org.kie.workbench.common.screens.home.model.HomeShortcutLink;
import org.kie.workbench.common.screens.home.model.ModelUtils;
import org.uberfire.backend.fs.FileSystemService;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.mvp.Command;

import static org.kie.workbench.common.services.shared.resources.PerspectiveIds.CONTENT_MANAGEMENT;
import static org.uberfire.workbench.model.ActivityResourceType.PERSPECTIVE;

@Alternative
@ApplicationScoped
public class HomeRuntimeProducer extends AbstractHomeProducer {

    private Caller<FileSystemService> fileSystemService;
    private FileSystemConfiguration configuration = new FileSystemConfiguration();
    private HomeConfiguration homeConfiguration;

    public HomeRuntimeProducer() {
        //CDI proxy
    }

    @Inject
    public HomeRuntimeProducer(final PlaceManager placeManager,
                               final TranslationService translationService,
                               final ShortcutHelper shortcutHelper,
                               Caller<FileSystemService> fileSystemService,
                               final HomeConfiguration homeConfiguration) {
        super(placeManager,
              translationService,
              shortcutHelper);
        this.fileSystemService = fileSystemService;
        this.homeConfiguration = homeConfiguration;
    }

    @Override
    public void initialize(final Runnable done) {
        this.fileSystemService.call(new RemoteCallback<Boolean>() {
            @Override
            public void callback(Boolean enabled) {
                configuration.setGitEnabled(enabled);
                done.run();
            }
        }).isGitDefaultFileSystem();
    }

    @Override
    protected void addProfileFullShortcuts(final HomeModel model) {

        if (configuration.isGitEnabled() && !homeConfiguration.isMonitoring()) {
            model.addShortcut(createDesignShortcut());
        }
        model.addShortcut(createDeployShortcut());
        model.addShortcut(createManageShortcut());
        model.addShortcut(createTrackShortcut());
    }

    @Override
    protected HomeShortcut createDesignShortcut() {
        final HomeShortcut design = ModelUtils.makeShortcut("pficon pficon-blueprint",
                                                            translationService.format(Constants.Design),
                                                            translationService.format(Constants.DesignRuntimeDescription),
                new Command() {
                    @Override
                    public void execute() {
                        placeManager.goTo(CONTENT_MANAGEMENT);
                    }
                },
                                                            CONTENT_MANAGEMENT,
                                                            PERSPECTIVE);
        design.addLink(new HomeShortcutLink(translationService.format(Constants.Pages),
                                            CONTENT_MANAGEMENT));

        return design;
    }
}
