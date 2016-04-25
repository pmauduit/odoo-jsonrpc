/*
 * Copyright 2016 QFast
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.odoo.rpc.entity;

import com.odoo.rpc.boundary.OeModuleService;
import com.odoo.rpc.util.OeUtil;

import java.util.Arrays;

/**
 * @author Ahmed El-mawaziny
 */
public class OeModule extends AbstractOeEntity<OeModuleService> {

    public static final String _SHORT_DESC = "shortdesc", _SUMMARY = "summary", _ICON = "icon", _STATE = "state",
            _AUTHOR = "author", _MENUS_BY_MODULE = "menus_by_module", _REPORTS_BY_MODULE = "reports_by_module",
            _MAINTAINER = "maintainer", _CONTRIBUTORS = "contributors", _SEQUENCE = "sequence", _DEMO = "demo",
            _VIEWS_BY_MODULE = "views_by_module", _DESCRIPTION_HTML = "description_html",
            _PUBLISHED_VERSION = "published_version", _INSTALLED_VERSION = "installed_version",
            _ICON_IMAGE = "icon_image", _APPLICATION = "application", _WEBSITE = "website", _DESCRIPTION = "description",
            _AUTO_INSTALL = "auto_install", _LICENSE = "license", _DEPENDENCIES_ID = "dependencies_id", _URL = "url",
            _LATEST_VERSION = "latest_version", _CATEGORY_ID = "category_id";

    public static final String[] COLUMNS = new String[]{_ID, _NAME, _CREATE_DATE, _CREATE_UID, _WRITE_DATE, _WRITE_UID,
            _DISPLAY_NAME, _LAST_UPDATE, _SHORT_DESC, _SUMMARY, _ICON, _STATE, _AUTHOR, _MENUS_BY_MODULE, _DEMO,
            _REPORTS_BY_MODULE, _MAINTAINER, _CONTRIBUTORS, _SEQUENCE, _VIEWS_BY_MODULE, _DESCRIPTION_HTML,
            _PUBLISHED_VERSION, _INSTALLED_VERSION, _ICON_IMAGE, _APPLICATION, _WEBSITE, _DESCRIPTION,
            _AUTO_INSTALL, _LICENSE, _DEPENDENCIES_ID, _URL, _LATEST_VERSION, _CATEGORY_ID};

    private static final long serialVersionUID = 1263824647955242480L;

    private String icon;
    private String shortDesc;
    private String summary;
    private String state;
    private String author;
    private String menusByModule;
    private Integer sequence;
    private boolean demo;
    private String viewsByModule;
    private String descriptionHtml;
    private String publishedVersion;
    private String installedVersion;
    private String iconImage;
    private boolean application;
    private String website;
    private boolean autoInstall;
    private String license;
    private Integer[] dependenciesId;
    private String url;
    private String latestVersion;
    private Object[] categoryId;
    private String maintainer;
    private String contributors;

    public OeModule() {
    }

    public OeModule(OeModuleService service) {
        super.oe = service;
    }

    @Override
    public String[] getColumns() {
        return COLUMNS;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMenusByModule() {
        return menusByModule;
    }

    public void setMenusByModule(String menusByModule) {
        this.menusByModule = menusByModule;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public boolean isDemo() {
        return demo;
    }

    public void setDemo(boolean demo) {
        this.demo = demo;
    }

    public String getViewsByModule() {
        return viewsByModule;
    }

    public void setViewsByModule(String viewsByModule) {
        this.viewsByModule = viewsByModule;
    }

    public String getDescriptionHtml() {
        return descriptionHtml;
    }

    public void setDescriptionHtml(String descriptionHtml) {
        this.descriptionHtml = descriptionHtml;
    }

    public String getPublishedVersion() {
        return publishedVersion;
    }

    public void setPublishedVersion(String publishedVersion) {
        this.publishedVersion = publishedVersion;
    }

    public String getInstalledVersion() {
        return installedVersion;
    }

    public void setInstalledVersion(String installedVersion) {
        this.installedVersion = installedVersion;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public boolean isApplication() {
        return application;
    }

    public void setApplication(boolean application) {
        this.application = application;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isAutoInstall() {
        return autoInstall;
    }

    public void setAutoInstall(boolean autoInstall) {
        this.autoInstall = autoInstall;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Integer[] getDependenciesId() {
        return dependenciesId;
    }

    public void setDependenciesId(Integer[] dependenciesId) {
        this.dependenciesId = dependenciesId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(String latestVersion) {
        this.latestVersion = latestVersion;
    }

    public Object[] getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Object[] categoryId) {
        this.categoryId = categoryId;
    }

    public String getMaintainer() {
        return maintainer;
    }

    public void setMaintainer(String maintainer) {
        this.maintainer = maintainer;
    }

    public String getContributors() {
        return contributors;
    }

    public void setContributors(String contributors) {
        this.contributors = contributors;
    }

    @Override
    public int hashCode() {
        return OeUtil.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass() && OeUtil.equals(this.id, ((OeModule) obj).id);
    }

    @Override
    public String toString() {
        return "OeModule{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", createDate=" + createDate +
                ", lastUpdate=" + lastUpdate +
                ", writeDate=" + writeDate +
                ", createUid=" + Arrays.toString(createUid) +
                ", writeUid=" + Arrays.toString(writeUid) +
                ", icon='" + icon + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", summary='" + summary + '\'' +
                ", state='" + state + '\'' +
                ", author='" + author + '\'' +
                ", menusByModule='" + menusByModule + '\'' +
                ", sequence=" + sequence +
                ", demo=" + demo +
                ", viewsByModule='" + viewsByModule + '\'' +
                ", descriptionHtml='" + descriptionHtml + '\'' +
                ", publishedVersion='" + publishedVersion + '\'' +
                ", installedVersion='" + installedVersion + '\'' +
                ", iconImage='" + iconImage + '\'' +
                ", application=" + application +
                ", website='" + website + '\'' +
                ", autoInstall=" + autoInstall +
                ", license='" + license + '\'' +
                ", dependenciesId=" + Arrays.toString(dependenciesId) +
                ", url='" + url + '\'' +
                ", latestVersion='" + latestVersion + '\'' +
                ", categoryId=" + Arrays.toString(categoryId) +
                ", maintainer='" + maintainer + '\'' +
                ", contributors='" + contributors + '\'' +
                '}';
    }
}
