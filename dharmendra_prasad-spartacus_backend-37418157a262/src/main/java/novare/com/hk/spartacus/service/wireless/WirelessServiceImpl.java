package novare.com.hk.spartacus.service.wireless;

import com.opencsv.CSVReader;
import novare.com.hk.spartacus.Repo.WirelessRepo;
import novare.com.hk.spartacus.model.Wireless;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class WirelessServiceImpl implements WirelessService {
    @Autowired
    private WirelessRepo wirelessRepo;

    @Override
    public String insert(MultipartFile file) {
        try{
            InputStream inputStream = file.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            CSVReader csvReader = new CSVReader(bufferedReader);
            List<String[]> records = csvReader.readAll();
            List<Wireless> wirelessSubscribers = new ArrayList<>();
            for (String[] record : records) {
                Wireless users = new Wireless();
                users.setMobileNumber(record[0]);
                users.setAmount(Long.parseLong(record[1]));
                users.setAccountNumber(record[2]);
                users.setName(record[3]);
                wirelessSubscribers.add(users);
            }
            wirelessRepo.saveAll(wirelessSubscribers);
            return "Success";
        }catch(IOException ex){
            System.out.println("Invalid FIle");
            return "Invalid File";
        }catch(ArrayIndexOutOfBoundsException ex){
            return "Invalid File Format";
        }
    }
}
