package com.android.insecurebankv2;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class ApplicationTest {

    private Context context;

    @Before
    public void setUp() {
        // Obtenha o contexto da aplicação usando o AndroidJUnit4
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void testApplicationContext() {
        // Verifique se o contexto da aplicação não é nulo
        assertNotNull(context);
    }
}
