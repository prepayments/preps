package io.github.prepayments.app.messaging;

import io.github.prepayments.app.messaging.filing.vm.ExcelViewModel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

/**
 * This class defines a common method for sending data into the queue especially data that pertains to information uploaded for data entry through use of a binary file.
 */
public class DataEntryMessageService implements MessageService<ExcelViewModel> {

    private final MessageChannel messageChannel;

    protected DataEntryMessageService(final MessageChannel messageChannel) {
        this.messageChannel = messageChannel;
    }

    public long sendMessage(final ExcelViewModel dataEntryItem) {

        if (messageChannel.send(MessageBuilder.withPayload(dataEntryItem).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build())) {

            return dataEntryItem.getRowIndex();
        }
        return 0;
    }
}
