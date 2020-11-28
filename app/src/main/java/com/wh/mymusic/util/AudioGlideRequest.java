//package com.wh.mymusic.util;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import androidx.annotation.NonNull;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.bumptech.glide.BitmapRequestBuilder;
//import com.bumptech.glide.DrawableRequestBuilder;
//import com.bumptech.glide.DrawableTypeRequest;
//import com.bumptech.glide.Priority;
//import com.bumptech.glide.RequestManager;
//import com.bumptech.glide.load.Key;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
//import com.bumptech.glide.request.target.Target;
//import com.wh.mymusic.R;
//import com.wh.mymusic.beam.Audio;
//import com.wh.mymusic.beam.AudioAlbum;
//
///**
// * @author Karim Abou Zeid (kabouzeid)
// */
//public class AudioGlideRequest {
//
//    private static final DiskCacheStrategy DEFAULT_DISK_CACHE_STRATEGY = DiskCacheStrategy.ALL;
//    private static final int DEFAULT_ERROR_IMAGE = R.drawable.default_artist_image;
//    public static final int DEFAULT_ANIMATION = android.R.anim.fade_in;
//
//    public static class Builder {
//        final RequestManager requestManager;
////        final Artist artist;
//        Audio audio;
//        boolean noCustomImage;
//
//        public static Builder from(@NonNull RequestManager requestManager, Audio audio) {
//            return new Builder(requestManager, audio);
//        }
//
//        private Builder(@NonNull RequestManager requestManager, Audio audio) {
//            this.requestManager = requestManager;
//            this.audio = audio;
//        }
//
//        public PaletteBuilder generatePalette(Context context) {
//            return new PaletteBuilder(this, context);
//        }
//
//        public BitmapBuilder asBitmap() {
//            return new BitmapBuilder(this);
//        }
//
//        public Builder noCustomImage(boolean noCustomImage) {
//            this.noCustomImage = noCustomImage;
//            return this;
//        }
//
//        public DrawableRequestBuilder<GlideDrawable> build() {
//            //noinspection unchecked
//            return createBaseRequest(requestManager, audio, noCustomImage)
//                    .diskCacheStrategy(DEFAULT_DISK_CACHE_STRATEGY)
//                    .error(DEFAULT_ERROR_IMAGE)
//                    .animate(DEFAULT_ANIMATION)
//                    .priority(Priority.LOW)
//                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                    .signature(Long.toString(System.currentTimeMillis()));
//        }
//    }
//
//    public static class BitmapBuilder {
//        private final Builder builder;
//
//        public BitmapBuilder(Builder builder) {
//            this.builder = builder;
//        }
//
//        public BitmapRequestBuilder<?, Bitmap> build() {
//            //noinspection unchecked
//            return createBaseRequest(builder.requestManager, builder.artist, builder.noCustomImage)
//                    .asBitmap()
//                    .diskCacheStrategy(DEFAULT_DISK_CACHE_STRATEGY)
//                    .error(DEFAULT_ERROR_IMAGE)
//                    .animate(DEFAULT_ANIMATION)
//                    .priority(Priority.LOW)
//                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                    .signature();
//        }
//    }
//
//    public static class PaletteBuilder {
//        final Context context;
//        private final Builder builder;
//
//        public PaletteBuilder(Builder builder, Context context) {
//            this.builder = builder;
//            this.context = context;
//        }
//
//        public BitmapRequestBuilder<?, BitmapPaletteWrapper> build() {
//            //noinspection unchecked
//            return createBaseRequest(builder.requestManager, builder.artist, builder.noCustomImage)
//                    .asBitmap()
//                    .transcode(new BitmapPaletteTranscoder(context), BitmapPaletteWrapper.class)
//                    .diskCacheStrategy(DEFAULT_DISK_CACHE_STRATEGY)
//                    .error(DEFAULT_ERROR_IMAGE)
//                    .animate(DEFAULT_ANIMATION)
//                    .priority(Priority.LOW)
//                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                    .signature(createSignature(builder.artist));
//        }
//    }
//
//    public static DrawableTypeRequest createBaseRequest(RequestManager requestManager, Audio audio, boolean noCustomImage) {
//        if (noCustomImage) {
//            final List<AlbumCover> songs = new ArrayList<>();
//            for (final Album album : artist.albums) {
//                final Song song = album.safeGetFirstSong();
//                songs.add(new AlbumCover(album.getYear(), song.data));
//            }
//            return requestManager.load(new ArtistImage(artist.getName(), songs));
//        } else {
//            return requestManager.load(CustomArtistImageUtil.getFile(artist));
//        }
//    }
//
//    private static Key createSignature(AudioAlbum album) {
//        return ArtistSignatureUtil.getInstance(App.getInstance()).getArtistSignature(album.get());
//    }
//}
