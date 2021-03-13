package com.yumtaufik.gitser.bottomsheet;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.yumtaufik.gitser.R;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class ProfileInfoBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    TextView tvProfileInfoEmail, tvProfileInfoGithubUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottom_sheet_profile_info, container, false);

        tvProfileInfoEmail = view.findViewById(R.id.tvProfileInfoEmail);
        tvProfileInfoGithubUrl = view.findViewById(R.id.tvProfileInfoGithubUrl);

        setInitOnClick();

        return view;
    }

    private void setInitOnClick() {
        tvProfileInfoEmail.setOnClickListener(this);
        tvProfileInfoGithubUrl.setOnClickListener(this);
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetMenuTheme;
    }

    private void stringEmail() {

        final String email = "yumtaufik1997@gmail.com";

        SpannableString spannableStringEmail = new SpannableString(email);

        ClickableSpan clickableSpanEmail = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
                intent.putExtra(Intent.EXTRA_EMAIL, email);
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");

                PackageManager packageManager = requireContext().getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                boolean isIntentSafe = activities.size() > 0;

                if (isIntentSafe) {
                    startActivity(Intent.createChooser(intent, "Send Email"));
                } else {
                    Toasty.warning(requireContext(), R.string.tvInstallEmailApp, Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
            }
        };

        spannableStringEmail.setSpan(clickableSpanEmail, 0, email.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvProfileInfoEmail.setText(spannableStringEmail);
        tvProfileInfoEmail.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void stringGithubUrl() {

        final String githubUrl = "https://github.com/yumtaufikhidayat/gitser";

        SpannableString spannableStringGithubUrl = new SpannableString(githubUrl);

        ClickableSpan clickableSpanGithubUrl = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl));

                PackageManager packageManager = requireContext().getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(browserIntent, PackageManager.MATCH_DEFAULT_ONLY);
                boolean isIntentSafe = activities.size() > 0;

                if (isIntentSafe) {
                    startActivity(Intent.createChooser(browserIntent, "Open with"));
                } else {
                    Toasty.warning(requireContext(), R.string.tvInstallBrowserApp, Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
            }
        };

        spannableStringGithubUrl.setSpan(clickableSpanGithubUrl, 0, githubUrl.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvProfileInfoGithubUrl.setText(spannableStringGithubUrl);
        tvProfileInfoGithubUrl.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvProfileInfoEmail:
                stringEmail();
                break;

            case R.id.tvProfileInfoGithubUrl:
                stringGithubUrl();
                break;
        }
    }
}
