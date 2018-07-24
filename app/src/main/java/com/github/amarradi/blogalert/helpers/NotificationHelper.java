/*
 * Copyright (C) 2015-2016 The Food Restriction Project Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.amarradi.blogalert.helpers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;

import com.github.amarradi.blogalert.R;

public class NotificationHelper extends ContextWrapper {
    private NotificationManager notifManager;

    private static final String CHANNEL_ONE_ID = "CHANNEL_ONE";
    private static final String CHANNEL_ONE_NAME = "Channel One";

    public NotificationHelper(Context ctx) {
        super(ctx);
        createChannels();
    }

    private void createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getManager().createNotificationChannel(notificationChannel);
        }
    }

    public Notification.Builder getNotification(String title,
                                                String body,
                                                Bitmap notifyPicture,
                                                PendingIntent pI,
                                                @ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new Notification.Builder(getApplicationContext(), CHANNEL_ONE_ID)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setColorized(true)
                    .setContentIntent(pI)
                    .setLargeIcon(notifyPicture)
                    .setSmallIcon(R.drawable.blogaler2t)
                    .setAutoCancel(true);

        } else {
            //noinspection deprecation
            return new Notification.Builder(getApplicationContext())
                    .setContentTitle(title)
                    .setContentText(body)
                    .setContentIntent(pI)
                    .setLargeIcon(notifyPicture)
                    .setSmallIcon(R.drawable.blogaler2t)
                    .setAutoCancel(true);
        }
    }

    public void notify(long id, Notification.Builder notification) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getManager().notify((int) id, notification.build());
        }
    }

    private NotificationManager getManager() {
        if (notifManager == null) {
            notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notifManager;
    }
}
