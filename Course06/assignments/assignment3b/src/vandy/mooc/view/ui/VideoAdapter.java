package vandy.mooc.view.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import vandy.mooc.R;
import vandy.mooc.model.mediator.VideoDataMediator;
import vandy.mooc.model.mediator.webdata.Video;
import vandy.mooc.model.services.LikeVideo;
import vandy.mooc.model.services.UnLikeVideo;
import vandy.mooc.model.services.UploadVideoService;
import vandy.mooc.presenter.VideoOps;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 * Show the view for each Video's meta-data in a ListView.
 */
public class VideoAdapter
       extends BaseAdapter {
    /**
     * Allows access to application-specific resources and classes.
     */
    private final Context mContext;
    
    
    private static VideoOps.View mView;
    
    /**
     * ArrayList to hold list of Videos that is shown in ListView.
     */
    private List<Video> videoList =
        new ArrayList<>();

    /**
     * Construtor that stores the Application Context.
     * 
     * @param Context
     */
    public VideoAdapter(Context context, VideoOps.View view) {
        super();
        mContext = context;
        mView = view;
    }

    /**
     * Method used by the ListView to "get" the "view" for each row of
     * data in the ListView.
     * 
     * @param position
     *            The position of the item within the adapter's data
     *            set of the item whose view we want. convertView The
     *            old view to reuse, if possible. Note: You should
     *            check that this view is non-null and of an
     *            appropriate type before using. If it is not possible
     *            to convert this view to display the correct data,
     *            this method can create a new view. Heterogeneous
     *            lists can specify their number of view types, so
     *            that this View is always of the right type (see
     *            getViewTypeCount() and getItemViewType(int)).
     * @param parent
     *            The parent that this view will eventually be
     *            attached to
     * @return A View corresponding to the data at the specified
     *         position.
     */
    public View getView(int position,
                        View convertView,
                        ViewGroup parent) {
        final Video video = videoList.get(position);

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =
                mInflater.inflate(R.layout.video_list_item, null);
        }

        TextView titleText =
            (TextView) convertView.findViewById(R.id.tvVideoTitle);
        titleText.setText(video.getName());
        
        titleText.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                displayUserLike(video.getId());
            }
        });
        
        
        CheckBox ckLike = (CheckBox)convertView.findViewById(R.id.ckLike);
        ckLike.setText(String.valueOf(video.getLikes()));
        ckLike.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				likeUnlikeVideo(video.getId(), isChecked);
				if(isChecked)
					buttonView.setText(String.valueOf(video.getLikes() + 1));
				else
					buttonView.setText(String.valueOf(video.getLikes()));
			}
        });
        
          
            
        return convertView;
    }
    
    private void displayUserLike(long id) {
    	
    }
    
    private void likeUnlikeVideo(long id, boolean chk) {
        if(chk)
    		new LikeVideo().execute(mView.getUser(), mView.getPassword(), String.valueOf(id));
        else
        	new UnLikeVideo().execute(mView.getUser(), mView.getPassword(), String.valueOf(id));
    }
    
    /**
     * Adds a Video to the Adapter and notify the change.
     */
    public void add(Video video) {
        videoList.add(video);
        notifyDataSetChanged();
    }

    /**
     * Removes a Video from the Adapter and notify the change.
     */
    public void remove(Video video) {
        videoList.remove(video);
        notifyDataSetChanged();
    }

    /**
     * Get the List of Videos from Adapter.
     */
    public List<Video> getVideos() {
        return videoList;
    }

    /**
     * Set the Adapter to list of Videos.
     */
    public void setVideos(List<Video> videos) {
        this.videoList = videos;
        notifyDataSetChanged();
    }

    /**
     * Get the no of videos in adapter.
     */
    public int getCount() {
        return videoList.size();
    }

    /**
     * Get video from a given position.
     */
    public Video getItem(int position) {
        return videoList.get(position);
    }

    /**
     * Get Id of video from a given position.
     */
    public long getItemId(int position) {
        return position;
    }
}
